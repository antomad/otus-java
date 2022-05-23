package ru.antomad.otus.hw05.bytebuddy.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class LogInterceptor {

    public void print(
            @Origin Method method,
            @AllArguments(nullIfEmpty = true) Object[] args) {
        String methodName = method.getName();

        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            sb.append(o);
            sb.append(",");
        }

        System.out.println("Invoked method: " +
                methodName +
                " with " + args.length + " arguments: " +
                sb.substring(0, sb.length() - 1));

    }
}