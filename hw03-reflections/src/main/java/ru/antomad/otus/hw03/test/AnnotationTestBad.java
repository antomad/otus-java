package ru.antomad.otus.hw03.test;

import ru.antomad.otus.hw03.annotation.After;
import ru.antomad.otus.hw03.annotation.Before;
import ru.antomad.otus.hw03.annotation.Test;

public class AnnotationTestBad {

    @Before
    public void warmUpOk() {
        System.out.println("AnnotationTestBad: warmUp");
    }

    @Before
    public void warmUpFail() throws Exception {
        System.out.println("AnnotationTestBad: failWarmUp");
        throw new Exception("AnnotationTestBad: warmUp failed with exception");
    }

    @After
    public void coolDown() {
        System.out.println("AnnotationTestBad: coolDown");
    }

    @Test
    public void failTest() throws Exception {
        System.out.println("AnnotationTestBad: failTest");
        throw new Exception("AnnotationTestBad: test failed with exception");
    }

    @Test
    public void successTest() {
        System.out.println("AnnotationTestBad: successTest");
    }

}