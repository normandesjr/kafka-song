package com.hibicode.kafkasong.twitterpooling.winner;

public class Winner {

    private String user;

    public String getUser() {
        return user;
    }

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
