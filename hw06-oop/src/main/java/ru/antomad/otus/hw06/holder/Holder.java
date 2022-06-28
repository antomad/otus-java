package ru.antomad.otus.hw06.holder;

import ru.antomad.otus.hw06.paper.Paper;

public interface Holder<T> {

    void put(T t);

    T get();

    int count();
}
