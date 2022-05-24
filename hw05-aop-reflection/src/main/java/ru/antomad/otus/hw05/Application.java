package ru.antomad.otus.hw05;

import ru.antomad.otus.hw05.service.Printer;
import ru.antomad.otus.hw05.wrapper.AopWrapper;
import ru.antomad.otus.hw05.wrapper.LogAopWrapper;

public class Application {

    public static void main(String[] args) {

        AopWrapper<Printer> wrapper = new LogAopWrapper<>();

        Printer printer = wrapper.getObjectByClassNameAndInterface("ru.antomad.otus.hw05.service.DefaultPrinter", Printer.class);

        printer.print("One string"); // expected no logging

        printer.print("The best ", "of choice is evolution"); // expected logging

        printer.print(300, " Spartans ", "is dead"); // expected logging
    }
}