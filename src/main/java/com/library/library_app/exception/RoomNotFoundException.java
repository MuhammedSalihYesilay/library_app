package com.library.library_app.exception;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String availabilitiesId){
        super("The room with id " + availabilitiesId + " was not found");
    }
}
