package ru.antomad.otus.hw05.bytebuddy;

import org.junit.jupiter.api.Test;
import ru.antomad.otus.hw05.bytebuddy.service.DefaultPrinter;
import ru.antomad.otus.hw05.bytebuddy.service.Printer;
import ru.antomad.otus.hw05.bytebuddy.wrapper.AopWrapper;
import ru.antomad.otus.hw05.bytebuddy.wrapper.LogAopWrapper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {

    private final ByteArrayOutputStream replacedOut = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Printer printer;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        System.setOut(new PrintStream(replacedOut));
        AopWrapper wrapper = new LogAopWrapper();
        printer = wrapper.getObjectByClass(DefaultPrinter.class);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldPrintOneArgumentWithOutLogging() {
        printer.print(1);
        assertFalse(replacedOut.toString().contains("Invoked method"));
    }

    @Test
    public void shouldPrintTwoArgumentWithLogging() {
        printer.print(1, 2);
        assertTrue(replacedOut.toString().contains("Invoked method"));
    }

    @Test
    public void shouldPrintThreeArgumentWithLogging() {
        printer.print(1, 2, 3);
        assertTrue(replacedOut.toString().contains("Invoked method"));
    }
}