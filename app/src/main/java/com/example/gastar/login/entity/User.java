package com.example.gastar.login.entity;

public class User {
    private String uid;
    private String name;
    private String lastName;
    private String email;
    private Boolean active;

    public User() {}

    public User(String uid, String name, String lastName, String email, Boolean active) {
        this.uid = uid;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public Boolean getActive() {
        return active;
    }

}
