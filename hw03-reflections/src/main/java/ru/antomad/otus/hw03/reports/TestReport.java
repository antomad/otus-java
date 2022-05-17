package ru.antomad.otus.hw03.reports;

public class TestReport {

    private final boolean success;
    private final String message;

    public TestReport(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}