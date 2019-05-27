package com.hibicode.kafkasong.twitterpooling.shell;

import com.hibicode.kafkasong.twitterpooling.twitter.Hashtag;
import com.hibicode.kafkasong.twitterpooling.twitter.SearchTweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ApplicationCommand {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationCommand.class);

    @Autowired
    private SearchTweet searchTweet;

    @ShellMethod("Start polling tweets")
    public void start(final String hashtag) {
        LOG.info("Starting search with hashtag: " + hashtag);
        final Hashtag searchableHashtag = new Hashtag(hashtag);
        searchTweet.start(searchableHashtag);
    }

    @ShellMethod("Stop pooling tweets")
    public void stop() {
        searchTweet.stop();
    }


}
