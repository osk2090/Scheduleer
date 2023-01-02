package com.www.scheduleer.controller.dto.member;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardInfo {
    private String title;
    private LocalDateTime createDate;
    private Boolean isCheck;
}
