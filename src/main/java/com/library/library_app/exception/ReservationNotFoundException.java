package com.library.library_app.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String reservationId) {
        super("Reservation with id " + reservationId + " not found");
    }

}
