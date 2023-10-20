package com.www.scheduleer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WebSocketTest {

    @Autowired
    private CustomWebSocketHandler customWebSocketHandler;


    @Test
    void connect() throws Exception {
//        String id = customWebSocketHandler.getConnectionAndGetId();
//        System.out.println(id);

    }
}
