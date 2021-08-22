package ru.antomad.otus.service;

import ru.antomad.otus.reports.ClassReport;

public interface ReportService {

    void collect(ClassReport classReport);

    void print();
}