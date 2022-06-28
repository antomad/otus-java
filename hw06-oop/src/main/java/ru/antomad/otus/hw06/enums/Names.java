package ru.antomad.otus.hw06.enums;

public enum Names {

    ONE_HUNDRED("ru.antomad.otus.hw06.paper.OneHundred"),
    TWO_HUNDRED("ru.antomad.otus.hw06.paper.TwoHundred"),
    FIVE_HUNDRED("ru.antomad.otus.hw06.paper.FiveHundred"),
    ONE_THOUSAND("ru.antomad.otus.hw06.paper.OneThousand"),
    TWO_THOUSAND("ru.antomad.otus.hw06.paper.TwoThousand"),
    FIVE_THOUSAND("ru.antomad.otus.hw06.paper.FiveThousand");

    private final String value;

    Names(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
