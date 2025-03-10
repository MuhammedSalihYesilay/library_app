package com.library.library_app.exception;

public class RoomGroupCapacityExceededException extends RuntimeException{

    private static final String MESSAGE = "Group room capacity cannot be less than 2";

    public RoomGroupCapacityExceededException() {
        super(MESSAGE);
    }
}
