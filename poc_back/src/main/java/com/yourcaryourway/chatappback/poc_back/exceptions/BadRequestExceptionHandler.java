package com.yourcaryourway.chatappback.poc_back.exceptions;

public class BadRequestExceptionHandler extends RuntimeException{

    public BadRequestExceptionHandler(String message) {
        super(message);
    }

    public BadRequestExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
