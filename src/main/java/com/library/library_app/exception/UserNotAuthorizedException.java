package com.library.library_app.exception;

public class UserNotAuthorizedException extends RuntimeException{

    private static final String MESSAGE = "Sadece admin yetkisine sahip kullanıcılar oda ekleyebilir.";

    public UserNotAuthorizedException() {
        super(MESSAGE);
    }
}
