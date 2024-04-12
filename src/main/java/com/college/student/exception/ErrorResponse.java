package com.college.student.exception;

import java.io.Serializable;

public class ErrorResponse extends RuntimeException implements Serializable {
    private int statusCode;
    private final String message;

    public ErrorResponse(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
