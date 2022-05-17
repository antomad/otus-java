package ru.antomad.otus.hw03.service;

import ru.antomad.otus.hw03.reports.ClassReport;

import java.util.List;

public interface TestService {

    List<ClassReport> runAllTestsFromClassesInPackage(String packageName);
}