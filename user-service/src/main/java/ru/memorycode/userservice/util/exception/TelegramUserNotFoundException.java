package ru.memorycode.userservice.util.exception;

public class TelegramUserNotFoundException extends RuntimeException {
    public TelegramUserNotFoundException(String message) {
        super(message);
    }
}
