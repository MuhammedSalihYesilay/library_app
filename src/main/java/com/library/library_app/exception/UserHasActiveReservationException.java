package com.library.library_app.exception;

public class UserHasActiveReservationException extends RuntimeException{
    public UserHasActiveReservationException(String userId){
        super("You cannot make a new reservation while you have an active reservation " + userId);
    }
}
