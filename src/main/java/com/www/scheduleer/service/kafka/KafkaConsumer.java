package com.www.scheduleer.service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {
    private static final String TOPIC = "scheduleer-kafka";
    @KafkaListener(topics = TOPIC, groupId = TOPIC)
    public void consume(String message) throws IOException {
        System.out.println(String.format("Consumed message : %s", message));
    }
}
