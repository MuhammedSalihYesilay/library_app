package com.library.library_app.exception;

public class EndTimeBeAfterException extends RuntimeException{

    private static final String MESSAGE = "End time must be after start time";

    public EndTimeBeAfterException() {
        super(MESSAGE);
    }
}
