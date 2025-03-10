package com.library.library_app.exception;

public class CustomUsernameNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Kullanıcı adı veya şifre hatalı";

    public CustomUsernameNotFoundException() {
        super(MESSAGE);
    }
}

