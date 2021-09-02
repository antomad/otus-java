package ru.antomad.otus.service;

import ru.antomad.otus.reports.ClassReport;
import ru.antomad.otus.reports.TestReport;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    @Override
    public void print(List<ClassReport> classReports) {
        System.out.println();
        for (ClassReport classReport : classReports) {
            System.out.println("Test class name: " + classReport.getClassName());
            if (classReport.getError() == null) {
                printSummary(classReport);
                for (TestReport testReport : classReport.getTestReports()) {
                    System.out.println("   Test report: " + testReport.getMessage());
                }
            } else {
                System.out.println("   Error message: " + classReport.getError());
            }
            System.out.println();
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