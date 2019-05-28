package com.hibicode.kafkasong.twitterpooling.winner;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Winner {

    private String user;

    public String getUser() {
        return user;
    }

    @JsonProperty("USER")
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "user='" + user + '\'' +
                '}';
    }
}
