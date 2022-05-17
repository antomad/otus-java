package ru.antomad.otus.hw03;

import ru.antomad.otus.hw03.service.*;
import ru.antomad.otus.hw03.service.*;
import ru.antomad.otus.hw03.service.*;

public class Application {

    public static void main(String[] args) {

        MessageService messageService = new MessageServiceImpl();
        ReportService reportService = new ReportServiceImpl();
        TestService testService = new TestServiceImpl(messageService);

        reportService.print(
                testService.runAllTestsFromClassesInPackage(
                        Application.class.getPackageName() + ".test"));
    }
}