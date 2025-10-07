package com.br.shortener.url.exceptions;

public class InvalidUuidException extends RuntimeException {
    public InvalidUuidException(String message) {
        super(message);
    }

    public InvalidUuidException(String message, Throwable cause) {
        super(message, cause);
    }
}
