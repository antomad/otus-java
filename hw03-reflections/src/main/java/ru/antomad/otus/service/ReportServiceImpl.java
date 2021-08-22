package ru.antomad.otus.service;

import ru.antomad.otus.reports.ClassReport;
import ru.antomad.otus.reports.TestReport;

import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private final List<ClassReport> classReports = new ArrayList<>();

    @Override
    public void collect(ClassReport classReport) {
        classReports.add(classReport);
    }

    @Override
    public void print() {
        for (ClassReport classReport : classReports) {
            System.out.println(classReport.getClassName());
            if (classReport.getError() == null) {
                printSummary(classReport);
                for (TestReport testReport : classReport.getTestReports()) {
                    System.out.println(testReport.getMessage());
                }
            } else {
                System.out.println(classReport.getError());
            }
        }
    }

    private void printSummary(ClassReport classReport) {
        int all = classReport.getTestReports().size();
        int success = (int) classReport.getTestReports().stream().filter(TestReport::isSuccess).count();
        int fail = all - success;
        System.out.println("All tests: " + all);
        System.out.println("Successful: " + success);
        System.out.println("Failed: " + fail);
    }
}