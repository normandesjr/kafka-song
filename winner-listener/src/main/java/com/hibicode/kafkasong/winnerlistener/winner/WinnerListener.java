package com.hibicode.kafkasong.winnerlistener.winner;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WinnerListener {

    private static final Logger LOG = LoggerFactory.getLogger(WinnerListener.class);

    @Autowired
    private Firestore firestore;

    @KafkaListener(topics = "WINNERS")
    public void winner(@Payload Winner winnerUser) {
        LOG.info("#### WINNER: " + winnerUser);
        saveToFirestore(winnerUser);
    }

    private void saveToFirestore(Winner winner) {
        final CollectionReference winnersCollection = firestore.collection("winners");

        try {
            writeDocument(winner, winnersCollection);
        } catch (Exception e) {
            LOG.error("Error writing to Firestore: ", e);
        }
    }

    private void writeDocument(Winner winner, CollectionReference winnersDocument) {
        final Map<String, Object> data = new HashMap<>();
        data.put("name", winner.getUser());
        data.put("time",winner.getTimestamp());
        winnersDocument.add(data);
    }

}
