package ru.antomad.otus.test;

import ru.antomad.otus.annotation.After;
import ru.antomad.otus.annotation.Before;
import ru.antomad.otus.annotation.Test;

public class AnnotationTestBad {

    @Before
    public void warmUpOk() {
        System.out.println("warmUp");
    }

    @Before
    public void warmUpFail() throws Exception {
        System.out.println("failWarmUp");
        throw new Exception("warmUp failed with exception");
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