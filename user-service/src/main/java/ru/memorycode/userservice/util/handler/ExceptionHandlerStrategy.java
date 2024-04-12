package ru.memorycode.userservice.util.handler;

import org.springframework.http.ProblemDetail;

public interface ExceptionHandlerStrategy {

     ProblemDetail execute(RuntimeException e);

     Class<? extends RuntimeException> getExceptionClass();
}
