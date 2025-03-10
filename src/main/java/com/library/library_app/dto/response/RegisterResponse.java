package com.library.library_app.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {

    private String message;

    public RegisterResponse() {
        this.message = "User successfully registered.";
    }
}
