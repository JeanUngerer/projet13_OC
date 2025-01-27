package com.yourcaryourway.chatappback.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {com.yourcaryourway.chatappback.exceptions.BadRequestExceptionHandler.class})
    public ResponseEntity<Object> handleAPIBadRequestException(com.yourcaryourway.chatappback.exceptions.BadRequestExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {com.yourcaryourway.chatappback.exceptions.NotFoundExceptionHandler.class})
    public ResponseEntity<Object> handleAPINotFoundException(com.yourcaryourway.chatappback.exceptions.NotFoundExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {com.yourcaryourway.chatappback.exceptions.ForbidenExceptionHandler.class})
    public ResponseEntity<Object> handleAPIForbidenException(com.yourcaryourway.chatappback.exceptions.ForbidenExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {com.yourcaryourway.chatappback.exceptions.UnauthorizedExceptionHandler.class})
    public ResponseEntity<Object> handleAPIUnauthorizedException(com.yourcaryourway.chatappback.exceptions.UnauthorizedExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {com.yourcaryourway.chatappback.exceptions.InternalErrorExceptionHandler.class})
    public ResponseEntity<Object> handleAPIInternalException(com.yourcaryourway.chatappback.exceptions.InternalErrorExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
