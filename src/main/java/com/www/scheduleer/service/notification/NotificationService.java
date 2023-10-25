package com.www.scheduleer.service.notification;

import com.www.scheduleer.Repository.NotificationRepository;
import com.www.scheduleer.controller.dto.command.CommandDto;
import com.www.scheduleer.controller.dto.notification.NotificationSaveDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.Notification;
import com.www.scheduleer.domain.enums.YN;
import com.www.scheduleer.service.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SseEmitters sseEmitters;

    public void findAllAndIsRead(Long memberId, YN yn) {
        List<Notification> notis = notificationRepository.findAllByReceiver_IdAndIsRead(memberId, yn);
        if (!notis.isEmpty()) {

        }
    }

    public Long save(NotificationSaveDto notificationSaveDto, Member member) {
        return notificationRepository.save(Notification.createNotification(notificationSaveDto, member)).getId();
    }

    public void sendNotificationToSSE(CommandDto commandDto) {
        // notification 데이터 select
        Optional<Notification> noti = notificationRepository.findById(commandDto.getData().getNotificationId());
        // sse를 통한 알림 전송
        noti.ifPresent(notification -> sseEmitters.sendMessage(commandDto.getData().getToId(), commandDto.getCommandType(), notification.getMessage()));
    }
}
