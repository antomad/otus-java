package ru.antomad.otus;

import ru.antomad.otus.annotation.After;
import ru.antomad.otus.annotation.Before;
import ru.antomad.otus.annotation.Test;

public class AnnotationTest {

    @Before
    public void warmUp() {
        System.out.println("warmUp");
    }

    @After
    public void coolDown() {
        System.out.println("coolDown");
    }

    @Test
    public void failTest() throws Exception {
        System.out.println("failTest");
        throw new Exception("test failed with exception");
    }

    @Test
    public void successTest() {
        System.out.println("successTest");
    }
}