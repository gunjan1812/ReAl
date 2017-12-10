package com.aligarh.real.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity(name = "Liked")
public class Like {
    @Id
    @NotNull
    private String username;

    public Like(){}

    public Like(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Like{" +
                "username='" + username + '\'' +
                '}';
    }
}
