package com.hibicode.kafkasong.twitterpooling.twitter;

public class Hashtag {

    private final String originalHashtag;
    private final String searchableHashtag;

    public Hashtag(String originalHashtag) {
        this.originalHashtag = originalHashtag;
        this.searchableHashtag = prepareHashtag(originalHashtag);
    }

    public String get() {
        return searchableHashtag;
    }

    private String prepareHashtag(final String hashtag) {
        String searchableHashtag = hashtag;
        if (!hashtag.startsWith("#")) {
            searchableHashtag = "#" + hashtag;
        }
        return searchableHashtag;
    }

    @Override
    public String toString() {
        return "Hashtag{" +
                "searchableHashtag='" + searchableHashtag + '\'' +
                '}';
    }
}
