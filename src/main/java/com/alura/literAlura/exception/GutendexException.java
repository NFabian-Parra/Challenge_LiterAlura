package com.alura.literAlura.exception;

public class GutendexException extends RuntimeException {
    public GutendexException(String message) {
        super(message);
    }

    public GutendexException(String message, Throwable cause) {
        super(message, cause);
    }
}
