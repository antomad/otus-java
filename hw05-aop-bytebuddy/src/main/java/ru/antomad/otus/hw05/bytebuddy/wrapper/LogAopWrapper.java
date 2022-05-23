package ru.antomad.otus.hw05.bytebuddy.wrapper;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import ru.antomad.otus.hw05.bytebuddy.annotation.Log;
import ru.antomad.otus.hw05.bytebuddy.interceptor.LogInterceptor;
import ru.antomad.otus.hw05.bytebuddy.service.Printer;

import java.lang.reflect.InvocationTargetException;

public class LogAopWrapper implements AopWrapper {

    public Printer getObjectByClass(Class<? extends Printer> printer) {

        try {

            return new ByteBuddy()
                    .subclass(printer)
                    .method(ElementMatchers.isAnnotatedWith(Log.class))
                    .intercept(MethodDelegation.to(new LogInterceptor()).andThen(SuperMethodCall.INSTANCE))
                    .make()
                    .load(printer.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded()
                    .getConstructor()
                    .newInstance();

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}