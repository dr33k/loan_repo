package com.agicomputers.LoanAPI.models.request;

public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;

    UsernameAndPasswordAuthenticationRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
