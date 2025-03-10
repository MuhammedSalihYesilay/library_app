package com.library.library_app.exception;

public class RoomMaxGroupSizeExceededException extends RuntimeException{

    private static final String MESSAGE = "Max group size cannot be greater than room capacity";

    public RoomMaxGroupSizeExceededException() {
        super(MESSAGE);
    }
}
