package com.www.scheduleer.controller.dto.board;

import com.www.scheduleer.controller.dto.reply.ReplyResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class BoardDetailDto {
    private String title;
    private String nickName;
    private String picture;
    private int views;
    private Boolean isCheck;
    private LocalDateTime regDate;
    private List<ReplyResponseDto> replyResponse;
}

