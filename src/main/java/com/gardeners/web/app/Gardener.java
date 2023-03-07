package com.gardeners.web.app;

import java.sql.Timestamp;

public class Gardener {

    int gardener_id;
    String username;
    String email;
    String password;

    public int getGardener_id() {
        return gardener_id;
    }

    public void setGardener_id(int gardener_id) {
        this.gardener_id = gardener_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
