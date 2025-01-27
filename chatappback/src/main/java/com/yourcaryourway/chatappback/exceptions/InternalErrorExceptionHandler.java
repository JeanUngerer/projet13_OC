package com.yourcaryourway.chatappback.exceptions;

public class InternalErrorExceptionHandler extends RuntimeException {

    public InternalErrorExceptionHandler(String message) {
        super(message);
    }

    public InternalErrorExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
