package com.yourcaryourway.chatappback.poc_back.exceptions;

public class UnauthorizedExceptionHandler extends RuntimeException{

    public UnauthorizedExceptionHandler(String message) {
        super(message);
    }

    public UnauthorizedExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
