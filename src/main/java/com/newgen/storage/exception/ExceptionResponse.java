package com.newgen.storage.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExceptionResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;


    /**
     * Constructs an ExceptionResponse object with the details of the provided exception.
     *
     * @param e      The exception that occurred, used to extract error details.
     * @param status The HTTP status code associated with the exception.
     * @param path   The path where the exception occurred.
     */
    public ExceptionResponse(Exception e, int status, String path) {
        this.timestamp = Instant.now().toString();
        this.status = status;
        this.error = e.getClass().getSimpleName();
        this.message = e.getMessage();
        this.path = path;
    }


}
