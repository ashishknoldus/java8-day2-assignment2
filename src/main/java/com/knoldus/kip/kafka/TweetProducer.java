package com.knoldus.kip.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knoldus.kip.common.TwitterConfiguration;
import lombok.Value;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;

import java.util.Properties;

@Value
public class TweetProducer {

    private String topicName;
    private Properties configProperties = new Properties();
    private KafkaProducer<String, String> producer;
    private ObjectMapper mapper = new ObjectMapper();

    StatusListener listener = new StatusListener() {

        public void onStatus(Status status) {
            try {
                String statusJson = mapper.writeValueAsString(status);
                ProducerRecord<String, String> rec = new ProducerRecord<String, String>(topicName, statusJson);
                producer.send(rec);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        }

        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        }

        public void onScrubGeo(long l, long l1) {
        }

        public void onStallWarning(StallWarning stallWarning) {
        }

        public void onException(Exception ex) {
        }
    };

    public TweetProducer(String topicName) {
        this.topicName = topicName;

        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG, "simple");

        producer = new KafkaProducer<>(configProperties);

        TwitterStream twitterStream = new TwitterStreamFactory(TwitterConfiguration.getTwitterConf()).getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
    }

}
