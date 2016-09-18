package com.example.keepair.myapplication.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Keepair on 2016-09-17.
 */
public class LoginData {

    @Expose
    public String username;

    @Expose
    public String password;

    @Expose
    public String email;

    public LoginData(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
