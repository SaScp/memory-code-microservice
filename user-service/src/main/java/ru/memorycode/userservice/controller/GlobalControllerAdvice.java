package ru.memorycode.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.memorycode.userservice.util.exception.UserNotFoundException;
import ru.memorycode.userservice.util.handler.ExceptionHandlerStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalControllerAdvice {

    private Map<Class<? extends RuntimeException>, ExceptionHandlerStrategy> handlers;

    public GlobalControllerAdvice(List<ExceptionHandlerStrategy> handlers) {
        this.handlers = new HashMap<>();
       for (var i : handlers) {
           this.handlers.put(i.getExceptionClass(), i);
       }
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ProblemDetail> exHandler(RuntimeException e) {
        ProblemDetail problemDetail = handlers.get(e.getClass()).execute(e);
        return ResponseEntity
                .status(problemDetail.getStatus())
                .body(problemDetail);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ProblemDetail> globalExHandler(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }
}
