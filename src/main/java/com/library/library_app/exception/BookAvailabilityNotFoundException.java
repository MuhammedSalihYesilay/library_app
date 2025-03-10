package com.library.library_app.exception;

public class BookAvailabilityNotFoundException extends RuntimeException {
    public BookAvailabilityNotFoundException(String bookId) {
        super("Book availability not found for book with id: " + bookId);
    }
}
