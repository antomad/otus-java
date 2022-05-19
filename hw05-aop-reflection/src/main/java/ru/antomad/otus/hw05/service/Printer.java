package ru.antomad.otus.hw05.service;

import ru.antomad.otus.hw05.annotation.Log;

public interface Printer {

    void print(Object o);

    @Log
    void print(Object o1, Object o2);

    @Log
    void print(Object o1, Object o2, Object o3);

}