package com.hibicode.kafkasong.twitterpooling.guess;

import org.springframework.context.ApplicationEvent;

public class GuessEvent extends ApplicationEvent {

    private Guess guess;

    public GuessEvent(Object source, Guess guess) {
        super(source);
        this.guess = guess;
    }

    public Guess getGuess() {
        return guess;
    }

}
