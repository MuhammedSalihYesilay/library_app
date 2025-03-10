package com.library.library_app.exception;

public class ExpiredPeriodException extends RuntimeException {

    private static final String MESSAGE = "Cannot set availability for expired period";

    public ExpiredPeriodException() {
        super(MESSAGE);
    }
}
