package com.library.library_app.exception;




public class MaxBooksExceededException extends RuntimeException {

    private static final String MESSAGE = "Bir öğrenci en fazla " + 3 + " kitap ödünç alabilir.";

    public MaxBooksExceededException() {
        super(MESSAGE);
    }
}
