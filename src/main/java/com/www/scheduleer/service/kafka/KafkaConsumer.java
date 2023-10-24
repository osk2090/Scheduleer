package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.command.CommandDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final KafkaServiceImpl kafkaService;

    @KafkaListener(topics = "${kafka.topic.notification}", containerFactory = "NotificationContainerFactory")
    public void sendMessage(@Payload CommandDto commandDto, Acknowledgment ack) {
        log.info("Kafka received message {}", commandDto);
        // 해당 메시지를 처리할 method 추가
        kafkaService.process(commandDto);

        ack.acknowledge();
    }

}
