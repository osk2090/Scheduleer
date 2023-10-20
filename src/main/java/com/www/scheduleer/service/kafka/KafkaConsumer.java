package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.noti.NotiDto;
import com.www.scheduleer.service.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final String TOPIC = "scheduleer-noti";
    private final SseEmitters sseEmitters;// sse 연결 정보

    @KafkaListener(topics = TOPIC, containerFactory = "NotificationContainerFactory")
    public void sendMessage(@Payload NotiDto notiDto, Acknowledgment ack) throws IOException {
        log.info("Kafka received message {}", notiDto);
        sseEmitters.sendMessage(notiDto.getMemberId(), notiDto);
        ack.acknowledge();
    }


}
