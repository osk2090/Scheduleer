package com.www.scheduleer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.websocket.WebSocketContainer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {

    private final CustomWebSocketHandler customWebSocketHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler, "/websocket").setAllowedOrigins("*");
    }
}
