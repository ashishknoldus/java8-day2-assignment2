package com.knoldus.kip;

import static com.knoldus.kip.common.TwitterConfiguration.twitterFactory;

import com.knoldus.kip.kafka.TweetConsumer;
import com.knoldus.kip.kafka.TweetProducer;
import twitter4j.*;

import java.util.List;
import java.util.Map;

public class MainRunner {
    public static void main(String[] args) {

        Map<String, String> envVars = System.getenv();
        for(String envName: envVars.keySet()) {
            System.out.println(envName);
        }

        Twitter twitter = twitterFactory.getInstance();
        try {
            QueryResult result = twitter.search(new Query("ghoutta"));
            List<Status> tweets = result.getTweets();
            for(Status tweet: tweets) {
                System.out.println("@" + tweet.getUser().getScreenName());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        TweetConsumer consumer = new TweetConsumer("groupID1", "topicName1");
        new TweetProducer("topicName1");
        consumer.startConsuming();
    }
}
