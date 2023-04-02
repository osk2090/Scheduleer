package com.www.scheduleer.controller.dto.reply;

import com.www.scheduleer.controller.dto.member.MemberToReplyDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReplyResponseDto {
    private Long id;
    private Long boardId;
    private Long reReplyId;
    private String comment;
    private MemberToReplyDto memberToReplyDto;
    private LocalDateTime regDate;
    private List<ReplyResponseDto> reReplyResponse;
}
