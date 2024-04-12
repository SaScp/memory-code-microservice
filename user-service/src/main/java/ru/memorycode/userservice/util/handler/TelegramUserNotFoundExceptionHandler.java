package ru.memorycode.userservice.util.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import ru.memorycode.userservice.util.exception.TelegramUserNotFoundException;

@Component
public class TelegramUserNotFoundExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ProblemDetail execute(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @Override
    public Class<? extends RuntimeException> getExceptionClass() {
        return TelegramUserNotFoundException.class;
    }
}
