package ru.antomad.otus.hw05.bytebuddy.service;

import ru.antomad.otus.hw05.bytebuddy.annotation.Log;

public class DefaultPrinter implements Printer {

    public DefaultPrinter() {
    }

    @Override
    public void print(Object o) {
        System.out.println("Print to console: " + o.toString());
    }

    @Log
    @Override
    public void print(Object o1, Object o2) {
        System.out.println("Print to console: " + o1 + o2);
    }

    @Log
    @Override
    public void print(Object o1, Object o2, Object o3) {
        System.out.println("Print to console: " + o1 + o2 + o3);
    }
}