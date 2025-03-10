package com.library.library_app.exception;

public class StudentIllegalArgumentException extends RuntimeException{

    private static final String MESSAGE = "Invalid student number: ";

    public StudentIllegalArgumentException(int studentNumber) {
        super(MESSAGE + ": " + studentNumber);
    }
}
