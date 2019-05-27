package com.hibicode.kafkasong.twitterpooling.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthorizationSpotify {

    @Value("${spotify.refreshToken}")
    private String refreshToken;

    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    public String requestRefreshedAccessToken() {
        final HttpHeaders httpHeaders = createHttpHeaders();
        final HttpEntity<MultiValueMap<String, String>> httpEntity = createHttpEntity(httpHeaders);
        final ResponseAccessTokenRefreshed accessTokenRefreshed = doRequestToSpotify(httpEntity);
        return accessTokenRefreshed.getAccessToken();
    }

    private ResponseAccessTokenRefreshed doRequestToSpotify(HttpEntity entity) {
        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<ResponseAccessTokenRefreshed> responseEntity = restTemplate
                .exchange(SpotifyURL.TOKEN, HttpMethod.POST, entity, ResponseAccessTokenRefreshed.class);
        if (responseEntity != null) {
            return responseEntity.getBody();
        } else {
            throw new RuntimeException("Error refreshing token from Spotify, the ResponseEntity is null.");
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createHttpEntity(HttpHeaders httpHeaders) {
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "refresh_token");
        requestBody.add("refresh_token", refreshToken);
        return new HttpEntity<>(requestBody, httpHeaders);

    }

    private HttpHeaders createHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth(clientId, clientSecret);
        return httpHeaders;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class ResponseAccessTokenRefreshed {

        @JsonProperty("access_token")
        private String accessToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }

}
