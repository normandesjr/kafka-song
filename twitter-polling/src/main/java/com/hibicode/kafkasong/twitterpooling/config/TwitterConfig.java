package com.hibicode.kafkasong.twitterpooling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
@RequestMapping
public class TwitterConfig  {

    @Bean
    @GetMapping
    public Twitter twitter(Environment env) {
        return new TwitterTemplate(
                env.getProperty("twitter.consumerKey"),
                env.getProperty("twitter.consumerSecret"));
    }



}
