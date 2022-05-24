package ru.antomad.otus.hw05.bytebuddy.wrapper;

import ru.antomad.otus.hw05.bytebuddy.service.Printer;

public interface AopWrapper {

    Printer getObjectByClass(Class<? extends Printer> printer);
}