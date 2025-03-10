package com.library.library_app.exception;

public class InvalidPeriodNumberException extends RuntimeException{
    public InvalidPeriodNumberException(int periodNumber){
        super("Period number must be between 1 and 6, " + periodNumber + " = I can't be");
    }
}
