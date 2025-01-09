package com.yourcaryourway.chatappback.poc_back.exceptions;

public class InternalErrorExceptionHandler extends RuntimeException {

    public InternalErrorExceptionHandler(String message) {
        super(message);
    }

    public InternalErrorExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
