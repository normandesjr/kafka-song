package com.hibicode.kafkasong.twitterpooling.winner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class WinnerListener {

    private static final Logger LOG = LoggerFactory.getLogger(WinnerListener.class);

    @KafkaListener(topics = "WINNERS")
    public void winner(@Payload Winner winnerUser) {
        LOG.info("#### WINNER: " + winnerUser);
    }

}
