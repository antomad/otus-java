package ru.antomad.otus.hw05.handler;

import ru.antomad.otus.hw05.annotation.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogInvocationHandler<T> implements InvocationHandler {
    private final T logger;

    public LogInvocationHandler(T logger) {
        this.logger = logger;
    }

    //TODO Просто один раз в конструкторе просканировать и потом переиспользовать результаты сканирования.
    /*
    Рефлексия - довольно дорогой механизм, такой прокси будет вносить ощутимую задержку.
    Стоит минимизировать количество обращений к рефлексии в течении жизненного цикла экземпляра класса.
    Можно с чистой совестью принять что класс не будет меняться по ходу.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Log.class)) {

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
        return method.invoke(logger, args);
    }
}