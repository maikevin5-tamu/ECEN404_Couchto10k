package com.example.myloginapp;

public class FirebaseUserHelperClass {

    String email, password;

    public FirebaseUserHelperClass(String email) {

    }

    public FirebaseUserHelperClass(String email, String password) {
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
