package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.command.CommandDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${kafka.topic.notification}")
    private String TOPIC;

    private final KafkaTemplate<String, CommandDto> kafkaTemplate;

    @Transactional
    public void sendMessage(CommandDto commandDto) {
        kafkaTemplate.send(TOPIC, commandDto);
        log.info("Request send message to Kafka: {}", commandDto);
    }
}
