package com.library.library_app.exception;

public class RoomTypeNotFoundException extends RuntimeException{
    public RoomTypeNotFoundException(String roomTypeId){
        super("RoomType not found with id " + roomTypeId);
    }
}
