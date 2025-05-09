package com.br.shortener.url.domain.controllers;

import com.br.shortener.url.exceptions.UnsupportedEncodingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.br.shortener.url.domain.dto.ApiErrorResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ApiErrorResponse(BAD_REQUEST.value(), "Encoding URL to UTF-8 " + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnknownException(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
