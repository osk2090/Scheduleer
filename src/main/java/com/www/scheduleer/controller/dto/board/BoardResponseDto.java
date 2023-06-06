package com.www.scheduleer.controller.dto.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String nickName;
    private String picture;
    private int views;
    private Boolean isCheck;
    private LocalDateTime regDate;
}
