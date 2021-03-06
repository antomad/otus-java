package ru.antomad.otus.hw03.service;

import ru.antomad.otus.hw03.annotation.After;
import ru.antomad.otus.hw03.annotation.Before;
import ru.antomad.otus.hw03.annotation.Test;
import ru.antomad.otus.hw03.helper.ReflectionHelper;
import ru.antomad.otus.hw03.reports.ClassReport;
import ru.antomad.otus.hw03.reports.TestReport;

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

    public TestServiceImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    public List<ClassReport> runAllTestsFromClassesInPackage(String packageName) {

        List<ClassReport> classReports = new ArrayList<>();

        try {
            for (String className : getFullClassNamesFromPackage(packageName)) {
                classReports.add(runAllTestsFromClass(className));
            }
        } catch (IOException e) {
            logger.severe("Can't get resources");
        }

        return classReports;
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
                        runBeforeMethods(runFirst, object, runLast);

                        classReport.getTestReports().add(runTestMethod(method, object));

                        runAfterMethods(runLast, object, null);
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
            String message = messageService.getMessage("class");
            logger.severe(message);
            classReport = new ClassReport(
                    className,
                    message,
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

    private void runBeforeMethods(List<Method> before, Object object, List<Method> after) {
        try {
            for (Method method : before) {
                ReflectionHelper.callMethod(object, method.getName());
            }
        } catch (RuntimeException e) {
            runAfterMethods(after, object, e);
            throw e;
        }
    }

    private void runAfterMethods(List<Method> methods, Object object, Throwable previous) {
        try {
            for (Method method : methods) {
                ReflectionHelper.callMethod(object, method.getName());
            }
        } catch (RuntimeException e) {
            if (previous != null) {
                e.addSuppressed(previous);
            }
            throw e;
        }
    }

    private TestReport runTestMethod(Method method, Object object) {
        try {
            ReflectionHelper.callMethod(object, method.getName());
            return new TestReport(
                    true,
                    String.format(messageService.getMessage("success"),
                            method.getName()));
        } catch (RuntimeException e) {
            return new TestReport(
                    false,
                    String.format(messageService.getMessage("fail"),
                            method.getName(),
                            getRootCause(e).getMessage()));
        }
    }
}