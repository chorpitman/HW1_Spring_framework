package com.epam.model.impl;

import com.epam.model.User;

public class UserImpl implements User {
    private static long counterId = 0;
    private long id;
    private String name;
    private String email;

    public UserImpl( String name, String email) {
        this.id = counterId++;
        this.name = name;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}