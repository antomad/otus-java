package ru.antomad.otus.service;

import ru.antomad.otus.reports.ClassReport;

import java.util.List;

public interface ReportService {

    void print(List<ClassReport> classReports);
}