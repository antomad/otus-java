package ru.antomad.otus.hw05.wrapper;

import ru.antomad.otus.hw05.handler.LogInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class LogAopWrapper<T> implements AopWrapper<T> {

    @Override
    public T getObjectByClassNameAndInterface(String name, Class<T> face) {

        T target;

        try {
            Class<?> clazz = Class.forName(name);
            target = (T) Arrays.stream(clazz.getDeclaredConstructors()).findFirst().get().newInstance();
        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        InvocationHandler handler = new LogInvocationHandler<>(target);

        return (T) Proxy.newProxyInstance(LogAopWrapper.class.getClassLoader(), new Class<?>[]{face}, handler);
    }
}