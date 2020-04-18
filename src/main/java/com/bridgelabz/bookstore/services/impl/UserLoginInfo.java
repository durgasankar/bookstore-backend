package com.bridgelabz.bookstore.services.impl;

public class UserLoginInfo {
    private String token;
    private String firstName;
    private String role;

    public UserLoginInfo( String token, String firstName ) {
        this.token = token;
        this.firstName = firstName;
    }

    public UserLoginInfo( String token, String firstName, String role ) {
        this(token,firstName);
        this.role = role;
    }

    public String getToken() { return token; }

    public void setToken( String token ) { this.token = token; }

    public String getFirstName() { return firstName; }

    public void setFirstName( String firstName ) { this.firstName = firstName; }

    public String getRole() { return role; }

    public void setRole( String role ) { this.role = role; }

    @Override
    public String toString() {
        return "UserLoginInfo{" +
                "token='" + token + '\'' +
                ", firstName='" + firstName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
