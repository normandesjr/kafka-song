package com.hibicode.kafkasong.twitterpooling.winner;

import com.google.cloud.firestore.DocumentReference;
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
        final String winner = winnerUser.getUser();
        saveToFirestore(winner);
    }

    private void saveToFirestore(String winner) {
        final DocumentReference winnersDocument = firestore.collection("winners").document(winner);

        try {
            writeDocument(winner, winnersDocument);
        } catch (Exception e) {
            LOG.error("Error writing to Firestore: ", e);
        }
    }

    private void writeDocument(String winner, DocumentReference winnersDocument) {
        final Map<String, Object> data = new HashMap<>();
        data.put("name", winner);
        winnersDocument.set(data);
    }

}
