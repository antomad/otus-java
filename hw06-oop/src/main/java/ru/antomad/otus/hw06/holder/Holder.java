package ru.antomad.otus.hw06.holder;

public interface Holder<T> {

    void put(T t);

    T get();

    int count();
}
