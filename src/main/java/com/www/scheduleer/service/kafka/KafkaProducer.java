package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.noti.NotiDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class KafkaProducer {
    private static final String TOPIC = "scheduleer-noti";

    private final KafkaTemplate<String, NotiDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, NotiDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Long memberId, String message) {
        NotiDto notiDto = NotiDto.builder()
                .memberId(memberId)
                .localDateTime(LocalDateTime.now())
                .message(message)
                .build();

        log.info("Request send message to Kafka: {}", String.valueOf(notiDto));

        kafkaTemplate.send(TOPIC, notiDto);
    }
}
