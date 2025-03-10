package com.library.library_app.exception;

public class BookOutOfStockException extends RuntimeException{

    private static final String MESSAGE = "Books out of stock";

    public BookOutOfStockException() {
        super(MESSAGE);
    }
}
