package com.yourcaryourway.chatappback.poc_back.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class APIException {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;

    public APIException(String message, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
