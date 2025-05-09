package com.br.shortener.url.exceptions;

public class UnsupportedEncodingException extends RuntimeException {
    public UnsupportedEncodingException(String message) {
        super(message);
    }

    public UnsupportedEncodingException(String message, Throwable cause) {
        super(message, cause);
    }
}
