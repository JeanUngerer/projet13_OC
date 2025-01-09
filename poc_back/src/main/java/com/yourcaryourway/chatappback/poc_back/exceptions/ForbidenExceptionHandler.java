package com.yourcaryourway.chatappback.poc_back.exceptions;

public class ForbidenExceptionHandler extends RuntimeException{

    public ForbidenExceptionHandler(String message) {
        super(message);
    }

    public ForbidenExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
