package com.library.library_app.exception;

public class UnauthorizedReservationDeletionException extends RuntimeException {
    public UnauthorizedReservationDeletionException(String message) {
        super(message);
    }
}