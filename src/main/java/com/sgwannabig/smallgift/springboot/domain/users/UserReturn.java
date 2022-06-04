package com.sgwannabig.smallgift.springboot.domain.users;

public class UserReturn {
    private int _id;
    private String username;
    private int __v;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public UserReturn(int _id, String username, int __v) {
        this._id = _id;
        this.username = username;
        this.__v = __v;
    }
}