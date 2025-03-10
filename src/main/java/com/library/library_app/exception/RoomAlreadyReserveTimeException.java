package com.library.library_app.exception;

public class RoomAlreadyReserveTimeException extends RuntimeException{

    private static final String MESSAGE = "Room is already reserved for the specified time period";

    public RoomAlreadyReserveTimeException()
    {
        super(MESSAGE);
    }
}
