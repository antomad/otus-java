package ru.antomad.otus.hw06.holder;

import java.util.ArrayDeque;
import java.util.Queue;

public class PaperHolder<Paper> implements Holder<Paper> {

    private final Queue<Paper> papers = new ArrayDeque<>();

    public void put(Paper paper) {
        papers.add(paper);
    }

    public Paper get() {
        return papers.poll();
    }

    public int count() {
        return papers.size();
    }
}