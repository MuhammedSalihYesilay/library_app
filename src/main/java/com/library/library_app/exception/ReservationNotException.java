package com.library.library_app.exception;

public class ReservationNotException extends RuntimeException{

    public ReservationNotException(String availabilityId) {
        super("Availability not found:"+ availabilityId);
    }
}
