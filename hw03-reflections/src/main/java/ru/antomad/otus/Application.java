package ru.antomad.otus;

import ru.antomad.otus.annotation.After;
import ru.antomad.otus.annotation.Before;
import ru.antomad.otus.annotation.Test;
import ru.antomad.otus.helper.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        testRunner("ru.antomad.otus.AnnotationTest");
    }

    public static void testRunner(String className) {

        final String FAIL = "Test %s failed with error %s";
        final String SUCCESS = "Test %s finished successfully";

        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getMethods();

            List<Method> runFirst = getMethodsAnnotatedWith(methods, Before.class);
            List<Method> runLast = getMethodsAnnotatedWith(methods, After.class);
            List<Method> runTest = getMethodsAnnotatedWith(methods, Test.class);

            List<String> result = new ArrayList<>();
            int countAll = runTest.size();
            AtomicInteger countFail = new AtomicInteger();
            AtomicInteger countSuccess = new AtomicInteger();

            if (countAll > 0) {
                runTest.forEach(testMethod -> {
                    Object object = ReflectionHelper.instantiate(clazz);
                    runFirst.forEach(beforeMethod -> ReflectionHelper.callMethod(object, beforeMethod.getName()));
                    try {
                        ReflectionHelper.callMethod(object, testMethod.getName());
                        countSuccess.getAndIncrement();
                        result.add(String.format(SUCCESS, testMethod.getName()));
                    } catch (Exception e) {
                        countFail.getAndIncrement();
                        result.add(String.format(FAIL, testMethod.getName(), e.getMessage()));
                    }
                    runLast.forEach(afterMethod -> ReflectionHelper.callMethod(object, afterMethod.getName()));
                });
            }

            logger.info("Test run: " + countAll);
            logger.info("Test failed: " + countFail);
            logger.info("Test passed: " + countSuccess);
            result.forEach(logger::info);

        } catch (ClassNotFoundException e) {
            logger.severe("Class not found");
        }
    }

    private static List<Method> getMethodsAnnotatedWith(
            Method[] methods,
            Class<? extends Annotation> annotationClass) {

        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

}
