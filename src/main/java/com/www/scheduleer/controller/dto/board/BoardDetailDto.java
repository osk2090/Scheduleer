package com.www.scheduleer.controller.dto.board;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDetailDto extends BoardResponseDto {

    private List<ReplyResponseDto> replyResponse;

    public BoardDetailDto(String title, String nickName, String picture, int views, Boolean isCheck, LocalDateTime regDate, List<ReplyResponseDto> replyResponse) {
        super(title, nickName, picture, views, isCheck, regDate);
        this.replyResponse = replyResponse;
    }
}
