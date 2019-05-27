package com.hibicode.kafkasong.twitterpooling.guess;

import java.io.Serializable;
import java.util.Objects;

public class Guess implements Serializable {

    private String user;
    private String song;

    public Guess(String user, String tweetText, String hashtag) {
        this.user = user;
        this.song = tweetText != null ? tweetText.replaceAll(hashtag, "").trim() : "";
    }

    public String getUser() {
        return user;
    }

    public String getSong() {
        return song;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guess guess = (Guess) o;
        return Objects.equals(user, guess.user) &&
                Objects.equals(song.toUpperCase(), guess.song.toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, song);
    }

    @Override
    public String toString() {
        return "Guess{" +
                "user='" + user + '\'' +
                ", song='" + song + '\'' +
                '}';
    }
}
