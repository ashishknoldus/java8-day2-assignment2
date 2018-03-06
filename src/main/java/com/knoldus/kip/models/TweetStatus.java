package com.knoldus.kip.models;

import lombok.Builder;
import lombok.Value;
import twitter4j.User;

import java.util.Date;

@Value
@Builder
public class TweetStatus {
    private Date createdAt;
    private long statusId;
    private String text;
    private long inReplyToStatusId;
    private long inReplyToUserId;
    private String inReplyToScreenName;
    private boolean isFavorited;
    private boolean isRetweeted;
    private int favoriteCount;
    private User user;
    private boolean isRetweet;
    private int retweetCount;
}
