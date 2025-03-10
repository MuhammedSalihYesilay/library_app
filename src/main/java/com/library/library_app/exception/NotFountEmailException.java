package com.library.library_app.exception;

public class NotFountEmailException extends RuntimeException{
    public NotFountEmailException(String email){
        super("Email must be in the format: studentNumber@msy.edu.tr" + " = " + email + " is not valid");
    }
}
