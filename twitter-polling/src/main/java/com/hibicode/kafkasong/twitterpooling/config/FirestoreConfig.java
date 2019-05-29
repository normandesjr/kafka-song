package com.hibicode.kafkasong.twitterpooling.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirestoreConfig {

    @Bean
    public Firestore firestore() throws IOException {
        final String accountKey = System.getProperty("user.home") + "/.gcp/kafka-song-credentials.json";
        final InputStream serviceAccount = new FileInputStream(accountKey);
        final FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://kafka-song.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        return FirestoreClient.getFirestore();
    }

}
