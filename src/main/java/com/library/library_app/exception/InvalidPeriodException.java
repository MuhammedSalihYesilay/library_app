package com.library.library_app.exception;

public class InvalidPeriodException extends RuntimeException{
    private static final String MESSAGE = "Selected period is not available for reservation";
    public InvalidPeriodException() {
        super(MESSAGE);
    }
}
