package ru.antomad.otus.hw05.wrapper;

public interface AopWrapper<T> {

    T getObjectByClassNameAndInterface(String name, Class<T> clazz);

}