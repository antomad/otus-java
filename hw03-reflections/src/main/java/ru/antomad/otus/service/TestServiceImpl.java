package ru.antomad.otus.service;

import ru.antomad.otus.annotation.After;
import ru.antomad.otus.annotation.Before;
import ru.antomad.otus.annotation.Test;
import ru.antomad.otus.helper.ReflectionHelper;
import ru.antomad.otus.reports.ClassReport;
import ru.antomad.otus.reports.TestReport;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final MessageService messageService;
    private final ReportService reportService;

    public TestServiceImpl(MessageService messageService,
                           ReportService reportService) {
        this.messageService = messageService;
        this.reportService = reportService;
    }

    public void runAllFromPackage(String packageName) {
        try {
            for (String className : getFullClassNamesFromPackage(packageName)) {
                reportService.collect(runAllTestsFromClass(className));
            }
        } catch (IOException e) {
            logger.severe("Can't get resources");
        }
    }

    public ClassReport runAllTestsFromClass(String className) {

        ClassReport classReport = new ClassReport(className, null, new ArrayList<>());

        try {

            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();

            List<Method> runFirst = getMethodsAnnotatedWith(methods, Before.class);
            List<Method> runLast = getMethodsAnnotatedWith(methods, After.class);
            List<Method> runTest = getMethodsAnnotatedWith(methods, Test.class);

            if (runTest.size() > 0) {
                for (Method method : runTest) {
                    try {
                        Object object = ReflectionHelper.instantiate(clazz);

                        runFirst.forEach(beforeMethod -> ReflectionHelper.callMethod(object, beforeMethod.getName()));

                        try {
                            ReflectionHelper.callMethod(object, method.getName());
                            classReport.getTestReports()
                                    .add(new TestReport(
                                            true,
                                            String.format(messageService.getMessage("success"),
                                                    method.getName())));
                        } catch (RuntimeException e) {
                            classReport.getTestReports()
                                    .add(new TestReport(
                                            false,
                                            String.format(messageService.getMessage("fail"),
                                                    method.getName(),
                                                    getRootCause(e).getMessage())));
                        }

                        runLast.forEach(afterMethod -> ReflectionHelper.callMethod(object, afterMethod.getName()));

                    } catch (RuntimeException e) {
                        classReport = new ClassReport(
                                className,
                                String.format(messageService.getMessage("error"), getRootCause(e).getMessage()),
                                null);
                        break;
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            logger.severe("Class not found");
            classReport = new ClassReport(
                    className,
                    "Class not found",
                    null);
        }
        return classReport;
    }

    private Throwable getRootCause(Exception e) {
        Throwable rootCause = e;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    private List<Method> getMethodsAnnotatedWith(
            Method[] methods,
            Class<? extends Annotation> annotationClass) {

        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    private List<String> getFullClassNamesFromPackage(String packageName) throws IOException {
        List<String> fileNames = new ArrayList<>();
        Enumeration<URL> resourcesUrls = Thread.currentThread()
                .getContextClassLoader()
                .getResources(packageName.replace(".", "/"));
        while (resourcesUrls.hasMoreElements()) {
            URL url = resourcesUrls.nextElement();
            File directory = new File(url.getFile());
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                String fileName = file.getName();
                if (fileName.endsWith(".class")) {
                    int dotPosition = fileName.lastIndexOf(".");
                    if (dotPosition > 0 && dotPosition < (fileName.length() - 1)) {
                        fileName = fileName.substring(0, dotPosition);
                    }
                    fileNames.add(packageName + "." + fileName);
                }
            }
        }
        return fileNames;
    }
}