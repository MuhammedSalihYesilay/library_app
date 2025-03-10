package com.library.library_app.exception;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String roomId) {
        super("Room " + roomId + " is not currently available for reservation");
    }
}
