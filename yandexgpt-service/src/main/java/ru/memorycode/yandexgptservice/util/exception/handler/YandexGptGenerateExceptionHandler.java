package ru.memorycode.yandexgptservice.util.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import ru.memorycode.yandexgptservice.util.exception.YandexGptGenerateException;

@Component
public class YandexGptGenerateExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ProblemDetail execute(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @Override
    public Class<? extends RuntimeException> getExceptionClass() {
        return YandexGptGenerateException.class;
    }
}
