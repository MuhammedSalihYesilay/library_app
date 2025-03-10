package com.library.library_app.exception;

public class RoomAvailabilityNotFoundException extends RuntimeException {
    public RoomAvailabilityNotFoundException(String roomId, int availability) {
        super("Room " + roomId + " is not currently available for reservation" + availability);
    }
}
