package com.www.scheduleer.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private Logger log = LoggerFactory.getLogger(getClass());

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("=== 웹소켓 입장 ===");
        CLIENTS.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("=== 웹소켓 퇴장 ===");
        CLIENTS.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  //메시지를 보낸 아이디
        log.info("session id = {}", id);
        CLIENTS.forEach((key, value) -> {
            log.info(String.valueOf(message));
            if (!key.equals(id)) {  //같은 아이디가 아니면 메시지를 전달합니다.
                try {
                    value.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
