package ru.antomad.otus.hw05.service;

public class DefaultPrinter implements Printer {

    @Override
    public void print(Object o) {
        System.out.println("Print to console: " + o.toString());
    }

    @Override
    public void print(Object o1, Object o2) {
        System.out.println("Print to console: " + o1 + o2);
    }

    @Override
    public void print(Object o1, Object o2, Object o3) {
        System.out.println("Print to console: " + o1 + o2 + o3);
    }
}