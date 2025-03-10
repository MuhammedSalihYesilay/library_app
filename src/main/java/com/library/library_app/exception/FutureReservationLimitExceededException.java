package com.library.library_app.exception;

public class FutureReservationLimitExceededException extends RuntimeException{
    public FutureReservationLimitExceededException(String roomId) {
        super("Reservations are only available for today and the next 2 days" + roomId);
    }
}
