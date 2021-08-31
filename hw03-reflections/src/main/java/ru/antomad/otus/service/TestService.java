package ru.antomad.otus.service;

import ru.antomad.otus.reports.ClassReport;

import java.util.List;

public interface TestService {

    List<ClassReport> runAllTestsFromClassesInPackage(String packageName);
}