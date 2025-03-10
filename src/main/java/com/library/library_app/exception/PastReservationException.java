package com.library.library_app.exception;

public class PastReservationException extends RuntimeException{

    private static final String MESSAGE = "Cannot cancel past reservations";

    public PastReservationException() {
        super(MESSAGE);
    }
}
