package com.library.library_app.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String studentId) {
        super("Student not found with id: " + studentId);
    }
}
