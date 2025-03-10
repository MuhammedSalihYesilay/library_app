package com.library.library_app.exception;

public class AlreadyBookReturnedException extends  RuntimeException{
    public AlreadyBookReturnedException(String checkoutId){
        super("Book already returned with id " + checkoutId);
    }
}
