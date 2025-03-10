package com.library.library_app.exception;

public class RoomReservationNotFoundException extends RuntimeException{

    public RoomReservationNotFoundException(String reservationId) {
        super("Reservation not found: " + reservationId);
    }
}
