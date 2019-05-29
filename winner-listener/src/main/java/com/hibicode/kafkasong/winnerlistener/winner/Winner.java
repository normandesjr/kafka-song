package com.hibicode.kafkasong.winnerlistener.winner;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Winner {

    private String user;
    private String timestamp;

    public String getUser() {
        return user;
    }

    @JsonProperty("USER")
    public void setUser(String user) {
        this.user = user;
    }


    public String getTimestamp() {
        return timestamp;
    }

    @JsonProperty("TIMESTAMP")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "user='" + user + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
