package com.hibicode.kafkasong.twitterpooling.guess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class GuessStore {

    private final Set<Guess> guesses = new HashSet<>();

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public boolean add(Guess guess) {
        boolean added = guesses.add(guess);

        if (added) {
            eventPublisher.publishEvent(new GuessEvent(this, guess));
        }

        return added;
    }

}
