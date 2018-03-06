package com.knoldus.kip.kafka;

import edu.emory.mathcs.backport.java.util.Arrays;
import lombok.Value;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

@Value
public class TweetConsumer {

    private String groupId;
    private String topicName;
    private Properties configProperties = new Properties();
    private KafkaConsumer<String, String> kafkaConsumer;

    public TweetConsumer(String groupId, String topicName) {
        this.groupId = groupId;
        this.topicName = topicName;

        configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configProperties.put(ConsumerConfig.CLIENT_ID_CONFIG, "simple");

        kafkaConsumer = new KafkaConsumer<String, String>(configProperties);
        kafkaConsumer.subscribe(Arrays.asList(new String[]{"topicName1"}));
    }

    public void startConsuming() {
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                String status = record.value();
                System.out.format("\n\nThe status string - %s", status);
            }
        }
    }

}
