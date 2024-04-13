package ru.memorycode.yandexgptservice.util.exception.handler;

import org.springframework.http.ProblemDetail;

public interface ExceptionHandlerStrategy {

    ProblemDetail execute(RuntimeException exception);

    Class<? extends RuntimeException> getExceptionClass();
}
