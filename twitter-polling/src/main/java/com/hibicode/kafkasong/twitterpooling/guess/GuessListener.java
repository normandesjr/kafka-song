package com.hibicode.kafkasong.twitterpooling.guess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GuessListener {

    private static final Logger LOG = LoggerFactory.getLogger(GuessListener.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Async
    @EventListener
    public void handleNewGuessStored(GuessEvent guessEvent) {
        final Guess guess = guessEvent.getGuess();
        LOG.info(guess.getUser() + ": " + guess.getSong());

        kafkaTemplate.send("guesses", guess);
    }

}
