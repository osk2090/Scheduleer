package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import com.www.scheduleer.controller.dto.command.DataType;
import com.www.scheduleer.controller.dto.notification.NotificationSaveDto;
import com.www.scheduleer.domain.enums.YN;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "type")
    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YN isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Builder
    public Notification(String message, DataType dataType, Member receiver) {
        this.message = message;
        this.dataType = dataType;
        this.isRead = YN.N;
        this.receiver = receiver;
    }

    public static Notification createNotification(NotificationSaveDto notificationSaveDto, Member member) {
        return Notification.builder()
                .message(notificationSaveDto.getMessage())
                .dataType(notificationSaveDto.getType())
                .receiver(member)
                .build();
    }

}
