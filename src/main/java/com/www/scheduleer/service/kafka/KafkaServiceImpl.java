package com.www.scheduleer.service.kafka;

import com.www.scheduleer.controller.dto.command.CommandDto;
import com.www.scheduleer.domain.enums.CommandType;
import com.www.scheduleer.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final NotificationService notificationService;
    @Override
    public void process(CommandDto commandDto) {
        switch (commandDto.getCommandType()) {
            default:
                log.info("read command: {}", commandDto.getCommandType());
            case NOTI:
                log.info("read data: {}", commandDto.getData());
                notificationService.sendNotificationToSSE(commandDto);
        }
    }
}
