package com.yourcaryourway.chatappback.poc_back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {BadRequestExceptionHandler.class})
    public ResponseEntity<Object> handleAPIBadRequestException(BadRequestExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundExceptionHandler.class})
    public ResponseEntity<Object> handleAPINotFoundException(NotFoundExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ForbidenExceptionHandler.class})
    public ResponseEntity<Object> handleAPIForbidenException(ForbidenExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UnauthorizedExceptionHandler.class})
    public ResponseEntity<Object> handleAPIUnauthorizedException(UnauthorizedExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {InternalErrorExceptionHandler.class})
    public ResponseEntity<Object> handleAPIInternalException(InternalErrorExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
