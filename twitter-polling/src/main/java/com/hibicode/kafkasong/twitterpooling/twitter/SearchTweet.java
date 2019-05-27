package com.hibicode.kafkasong.twitterpooling.twitter;

import com.hibicode.kafkasong.twitterpooling.guess.Guess;
import com.hibicode.kafkasong.twitterpooling.guess.GuessStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class SearchTweet {

    @Autowired
    private Twitter twitter;

    @Autowired
    private GuessStore guessStore;

    private Hashtag hashtag;

    private boolean search;

    @Scheduled(fixedRate = 2000)
    public void search() {
        if (search) {
            doSearch(hashtag);
        }
    }

    public void start(final Hashtag hashtag) {
        this.hashtag = hashtag;
        search = true;
    }

    public void stop() {
        search = false;
    }

    private void doSearch(Hashtag hashtag) {
        final SearchResults searchResults = twitter.searchOperations().search(hashtag.get());
        searchResults.getTweets().forEach(t -> guessStore.add(
                new Guess(t.getFromUser(), t.getText(), hashtag.get())));
    }
}
