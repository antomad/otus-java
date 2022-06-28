package ru.antomad.otus.hw06.atm;

import ru.antomad.otus.hw06.paper.Paper;

import java.util.List;

public interface Atm {

    void put(List<Paper> papers);

    List<Paper> pull(long sum);

    long balance();

}