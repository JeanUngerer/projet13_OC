package com.yourcaryourway.chatappback.exceptions;

public class UnauthorizedExceptionHandler extends RuntimeException{

    public UnauthorizedExceptionHandler(String message) {
        super(message);
    }

    public UnauthorizedExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
