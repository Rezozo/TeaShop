package com.tea.paradise;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    @SneakyThrows
    public ResponseEntity<String> handleThrowable(ServletWebRequest webRequest, Exception throwable) {
        return sendErrorResponse(webRequest, throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @SneakyThrows
    public ResponseEntity<String> handleConstraintViolationException(ServletWebRequest webRequest, ConstraintViolationException exception) {
        List<String> messages = ofNullable(exception.getConstraintViolations())
                .orElse(Collections.emptySet()).stream()
                .map(ConstraintViolation::getMessage)
                .collect(toList());
        String message = messages.isEmpty() ? exception.getMessage() : String.join(". ", messages) + ".";

        return sendErrorResponse(webRequest, message, HttpStatus.BAD_REQUEST.value());
    }

    @SneakyThrows
    private ResponseEntity<String> sendErrorResponse(ServletWebRequest webRequest, String message, int status) {
        if (webRequest.getResponse() == null) {
            return ResponseEntity
                    .internalServerError()
                    .build();
        }

        return ResponseEntity
                .status(status)
                .body(message);
    }
}
