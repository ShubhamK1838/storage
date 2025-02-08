package com.newgen.storage.exception.handler;

import com.newgen.storage.exception.ExceptionResponse;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Global exception handler for REST endpoints.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Handle generic exceptions.
     *
     * @param exception the exception that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity object containing the exception details and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(
                exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle specific custom exceptions, e.g., NotFoundException.
     *
     * @param exception the exception that was thrown.
     * @param request the current web request.
     * @return a ResponseEntity object containing the exception details and HTTP status.
     */
    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(ChangeSetPersister.NotFoundException exception, WebRequest request) {
        ExceptionResponse errorDetails = new ExceptionResponse(
                exception, HttpStatus.NOT_FOUND.value(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }



}
