package ru.antomad.otus;

import ru.antomad.otus.service.*;

public class Application {

    public static void main(String[] args) {

        MessageService messageService = new MessageServiceImpl();
        ReportService reportService = new ReportServiceImpl();
        TestService testService = new TestServiceImpl(messageService, reportService);

        testService.runAllFromPackage(Application.class.getPackageName() + ".test");
        reportService.print();
    }
}