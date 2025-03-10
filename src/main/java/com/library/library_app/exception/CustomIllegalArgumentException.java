package com.library.library_app.exception;

public class CustomIllegalArgumentException extends RuntimeException{
    public CustomIllegalArgumentException(String userId) {
        super("Username already exists, " + userId);
    }
}
