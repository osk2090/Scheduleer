package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.command.CommandDto;
import org.springframework.stereotype.Service;

public interface KafkaService {
    void process(CommandDto commandDto);
}
