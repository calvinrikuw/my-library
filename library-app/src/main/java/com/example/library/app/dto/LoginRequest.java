package com.example.library.app.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "account Or email is required")
    private String accountOrEmail;

    @NotBlank(message = "password is required")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String accountOrEmail, String password) {
        this.accountOrEmail = accountOrEmail;
        this.password = password;
    }

    public String getAccountOrEmail() {
        return accountOrEmail;
    }

    public void setAccountOrEmail(String accountOrEmail) {
        this.accountOrEmail = accountOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
