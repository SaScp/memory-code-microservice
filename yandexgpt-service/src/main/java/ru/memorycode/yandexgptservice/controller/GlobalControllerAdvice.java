package ru.memorycode.yandexgptservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.memorycode.yandexgptservice.util.exception.YandexGptGenerateException;
import ru.memorycode.yandexgptservice.util.exception.YandexGptProcessingException;
import ru.memorycode.yandexgptservice.util.exception.handler.ExceptionHandlerStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerAdvice {

    private Map<Class<? extends RuntimeException>, ExceptionHandlerStrategy> handlers;

    public GlobalControllerAdvice(List<ExceptionHandlerStrategy> handlers) {
        this.handlers = new HashMap<>();
        for (var i : handlers) {
            this.handlers.put(i.getExceptionClass(), i);
        }
    }

    @ExceptionHandler({YandexGptProcessingException.class})
    public ResponseEntity<HttpStatus> procExHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.PROCESSING).body(HttpStatus.PROCESSING);
    }

    @ExceptionHandler({YandexGptGenerateException.class})
    public ResponseEntity<ProblemDetail> exHandler(RuntimeException e) {
        ProblemDetail problemDetail = handlers.get(e.getClass()).execute(e);
        return ResponseEntity.status(problemDetail.getStatus()).body(problemDetail);
    }
}
