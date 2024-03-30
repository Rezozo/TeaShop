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

    /*
    @ExceptionHandler(Throwable.class)
    @SneakyThrows
    public ResponseEntity<String> handleThrowable(Throwable throwable) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(throwable.getMessage());
    }
    */
    @ExceptionHandler(ConstraintViolationException.class)
    @SneakyThrows
    public void handleConstraintViolationException(ConstraintViolationException exception, ServletWebRequest webRequest) {
        List<String> messages = ofNullable(exception.getConstraintViolations())
                .orElse(Collections.emptySet()).stream()
                .map(ConstraintViolation::getMessage)
                .collect(toList());
        String message = messages.isEmpty() ? exception.getMessage() : String.join(". ", messages) + ".";

        handleBadRequest(webRequest, message);
    }

    @SneakyThrows
    private <E extends Exception> void handleBadRequest(ServletWebRequest webRequest, String message) {
        if (webRequest.getResponse() == null) {
            return;
        }

        webRequest.getResponse()
                .sendError(HttpStatus.BAD_REQUEST.value(), message);
    }
}
