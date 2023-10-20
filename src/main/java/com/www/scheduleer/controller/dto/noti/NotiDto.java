package com.www.scheduleer.controller.dto.noti;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotiDto {
    private Long memberId;
    private String message;
    private LocalDateTime localDateTime;
}
