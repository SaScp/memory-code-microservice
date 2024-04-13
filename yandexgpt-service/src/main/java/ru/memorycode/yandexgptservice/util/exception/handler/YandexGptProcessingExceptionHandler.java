package ru.memorycode.yandexgptservice.util.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import ru.memorycode.yandexgptservice.util.exception.YandexGptProcessingException;

@Component
public class YandexGptProcessingExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public ProblemDetail execute(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.PROCESSING, exception.getMessage());
    }

    @Override
    public Class<? extends RuntimeException> getExceptionClass() {
        return YandexGptProcessingException.class;
    }
}
