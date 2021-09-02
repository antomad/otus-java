package ru.antomad.otus.service;

public class MessageServiceImpl implements MessageService {

    @Override
    public String getMessage(String code) {
        return Message.valueOf(code.toUpperCase()).getMessage();
    }

    enum Message {
        FAIL("Test %s failed with error: %s"),
        SUCCESS("Test %s finished successfully"),
        ERROR("General error. Message is: %s"),
        CLASS("Class not found");

        private final String message;

        Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}