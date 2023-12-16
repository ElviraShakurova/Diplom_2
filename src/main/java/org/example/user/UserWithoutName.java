package org.example.user;

public class UserWithoutName {
    private String email;
    private String password;

    public UserWithoutName() {
    }

    public UserWithoutName(String email, String password, String name) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}