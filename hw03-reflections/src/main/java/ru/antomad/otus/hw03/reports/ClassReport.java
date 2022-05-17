package ru.antomad.otus.hw03.reports;

import java.util.List;

public class ClassReport {

    private final String className;
    private final List<TestReport> testReports;
    private final String error;

    public ClassReport(String className, String error, List<TestReport> testReports) {
        this.className = className;
        this.error = error;
        this.testReports = testReports;
    }

    public List<TestReport> getTestReports() {
        return testReports;
    }

    public String getError() {
        return error;
    }

    public String getClassName() {
        return className;
    }
}