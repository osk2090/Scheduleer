package com.www.scheduleer.controller.dto.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KakfaObj {
    private DataType type;
    private Long toId; // 수신자 id
    private Long notificationId; // notification id
}
