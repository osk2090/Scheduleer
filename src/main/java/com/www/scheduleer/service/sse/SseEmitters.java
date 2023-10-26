package com.www.scheduleer.service.sse;

import com.www.scheduleer.controller.dto.command.DataType;
import com.www.scheduleer.domain.enums.CommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class SseEmitters {
    private static final ConcurrentHashMap<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Value("${sse.timeout}")
    private String sseTimeout;

    public boolean isValid(Long memberId) {
        return emitters.get(memberId) != null;
    }

    public ConcurrentHashMap<Long, SseEmitter> getSseMap() {
        return emitters;
    }

    public SseEmitter connect(Long memberId) {
        final Long LONG_TYPE_SSE_TIMEOUT = Long.parseLong(
                sseTimeout.replace("L", "")
        );

        SseEmitter emitter = new SseEmitter(LONG_TYPE_SSE_TIMEOUT);
        emitters.put(memberId, emitter);// 멀티 스레드를 통한 emitter 저장

        log.info("new emitter added: {}", emitter);
        log.info("emitter list size: {}", emitters.size());

        try {
            emitter.send(
                    SseEmitter.event()
                            .name("connect")
                            .data("success")
            );
            log.info("connect success: {}", emitter.toString());

            // sse 연결 만료 처리
            emitter.onCompletion(() -> {
                log.info("onCompletion callback");
                emitters.remove(emitter);    // 만료되면 리스트에서 삭제
            });
            emitter.onTimeout(() -> {
                log.info("onTimeout callback");
                emitter.complete();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return emitter;
    }

    public void sendMessage(Long memberId, CommandType commandType, String msg) {
        emitters.forEach((key, value) -> {
            if (key.equals(memberId)) {
                try {
                    value.send(
                            SseEmitter.event()
                                    .name(commandType.name())
                                    .data(msg)
                    );
                } catch (IOException e) {
                    log.warn(String.valueOf(e));
                }
            }
        });
    }
}