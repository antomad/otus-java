package ru.antomad.otus.hw05.bytebuddy;

import ru.antomad.otus.hw05.bytebuddy.service.DefaultPrinter;
import ru.antomad.otus.hw05.bytebuddy.service.Printer;
import ru.antomad.otus.hw05.bytebuddy.wrapper.AopWrapper;
import ru.antomad.otus.hw05.bytebuddy.wrapper.LogAopWrapper;

public class Application {

    public static void main(String[] args) {

        AopWrapper wrapper = new LogAopWrapper();

        Printer printer = wrapper.getObjectByClass(DefaultPrinter.class);

        printer.print("One string"); // expected no logging

        printer.print("The best ", "of choice is evolution"); // expected logging

        printer.print(300, " Spartans ", "is dead"); // expected logging//
    }
}