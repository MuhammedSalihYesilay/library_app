package com.library.library_app.exception;

public class RoomInvalidCapacityExceededException extends RuntimeException{

    private static final String MESSAGE = "Individual room capacity cannot be greater than 1";

    public RoomInvalidCapacityExceededException() {
        super(MESSAGE);
    }
}
