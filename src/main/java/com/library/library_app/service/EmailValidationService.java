package com.library.library_app.service;

import com.library.library_app.exception.NotFountEmailException;
import org.springframework.stereotype.Service;

@Service
public class EmailValidationService {

    public void validateStudentEmail(Integer studentNumber, String email) {
        String expectedEmail = studentNumber + "@msy.edu.tr";
        if (!email.equals(expectedEmail)) {
            throw new NotFountEmailException(email);
        }
    }
}
