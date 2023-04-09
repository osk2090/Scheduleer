package com.www.scheduleer.config.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "Scheduleer", groupId = "foo")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);
    }
}
