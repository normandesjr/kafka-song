package com.hibicode.kafkasong.spotifypolling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class SpotifyPollingApp {

    public static void main(String[] args) {
        SpringApplication.run(SpotifyPollingApp.class, args);
    }

}

