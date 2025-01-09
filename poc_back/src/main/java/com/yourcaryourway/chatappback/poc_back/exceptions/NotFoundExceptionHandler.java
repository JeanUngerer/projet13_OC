package com.yourcaryourway.chatappback.poc_back.exceptions;

public class NotFoundExceptionHandler extends RuntimeException{

    public NotFoundExceptionHandler(String message) {
        super(message);
    }

    public NotFoundExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
