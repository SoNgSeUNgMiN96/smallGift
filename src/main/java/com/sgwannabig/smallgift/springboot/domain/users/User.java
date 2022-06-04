package com.sgwannabig.smallgift.springboot.domain.users;

public class User {

    private String username;
    private String hashedPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return hashedPassword;
    }

    public void setPassword(String password) {
        this.hashedPassword = password;
    }

    public boolean includeNull() {
        if(username==null||hashedPassword==null) return true;
        return false;
    }


}
