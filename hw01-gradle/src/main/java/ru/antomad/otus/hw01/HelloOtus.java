package ru.antomad.otus.hw01;


import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {

        System.out.println("Hello Otus");

        List<String> sampleList = Lists.newArrayList("This", "is", "Guava", "example");

        String result = Joiner.on(" ").join(sampleList);

        System.out.println(result);

    }

}
