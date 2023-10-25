package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/sse/subscribe")
@RequiredArgsConstructor
@Slf4j
public class SseController {
    private final SseEmitters sseEmitters;

    @Value("${sse.timeout}")
    private String sseTimeout;

    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@CurrentMember Member member) throws IOException {
        // 1. 새로 sse 를 발급한다.
        // 메모리에 memberId,sse map 형식으로 저장한다음

        // 카프카를 통해 가져온 데이터의 member id값을 이용해서 해당 sse의 값을 찾은 다음

        // 해당 sse에 연결된 유저에게 메시지 발송한다.

        SseEmitter se = sseEmitters.connect(member.getId());

        return ResponseEntity.ok(se);
    }

}
