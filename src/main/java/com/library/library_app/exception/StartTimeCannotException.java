package com.library.library_app.exception;

public class StartTimeCannotException extends RuntimeException{

    private static final String MESSAGE = "Reservation start time cannot be in the past";

    public StartTimeCannotException() {
        super(MESSAGE);
    }
}
