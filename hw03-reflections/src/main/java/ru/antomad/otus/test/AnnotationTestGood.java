package ru.antomad.otus.test;

import ru.antomad.otus.annotation.After;
import ru.antomad.otus.annotation.Before;
import ru.antomad.otus.annotation.Test;

public class AnnotationTestGood {

    @Before
    public void warmUp() {
        System.out.println("AnnotationTestGood: warmUp");
    }

    @After
    public void coolDown() {
        System.out.println("AnnotationTestGood: coolDown");
    }

    @Test
    public void failTest() throws Exception {
        System.out.println("AnnotationTestGood: failTest");
        throw new Exception("AnnotationTestGood: test failed with exception");
    }

    @Test
    public void successTest() {
        System.out.println("AnnotationTestGood: successTest");
    }
}