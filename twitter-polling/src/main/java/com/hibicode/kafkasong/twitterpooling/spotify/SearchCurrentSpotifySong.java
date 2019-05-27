package com.hibicode.kafkasong.twitterpooling.spotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class SearchCurrentSpotifySong {

    private static final Logger LOG = LoggerFactory.getLogger(SearchCurrentSpotifySong.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private AuthorizationSpotify authorizationSpotify;

    private String accessToken = "initial-access-token";
    private String currentSong;

    @Scheduled(fixedRate = 2000, initialDelay = 10000)
    public void search() {
        try {
            doSearch();
        } catch (HttpClientErrorException ex) {
            handleAccessDenied(ex);
        }
    }

    private void doSearch() {
        final SpotifySong spotifySong = requestCurrentSongFromSpotify();
        if (isSongChanged(spotifySong)) {
            sendToKafka(spotifySong);
        }
    }

    private SpotifySong requestCurrentSongFromSpotify() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(accessToken);
        final HttpEntity entity = new HttpEntity(httpHeaders);

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<SpotifySong> responseEntity = restTemplate
                .exchange(SpotifyURL.CURRENTLY_PLAYING, HttpMethod.GET, entity, SpotifySong.class);

        return responseEntity.getBody();
    }

    private boolean isSongChanged(SpotifySong spotifySong) {
        return spotifySong != null && !spotifySong.getName().equals(currentSong);
    }

    private void sendToKafka(SpotifySong spotifySong) {
        this.currentSong = spotifySong.getName();
        LOG.info("Current song: " + this.currentSong);
        kafkaTemplate.send("current_song", spotifySong);
    }

    private void handleAccessDenied(HttpClientErrorException ex) {
        if (HttpStatus.UNAUTHORIZED.equals(ex.getStatusCode())) {
            this.accessToken = authorizationSpotify.requestRefreshedAccessToken();
        } else {
            throw ex;
        }
    }

}
