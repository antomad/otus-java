package ru.antomad.otus.service;

public class MessageServiceImpl implements MessageService {

    @Override
    public String getMessage(String code) {
        return Message.valueOf(code.toUpperCase()).message;
    }

    enum Message {
        FAIL("Test %s failed with error: %s"),
        SUCCESS("Test %s finished successfully"),
        ERROR("General error. Game over. The horse is dead. Message is: %s");

        public final String message;

        Message(String message) {
            this.message = message;
        }
    }
}