package com.www.scheduleer.controller.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplySaveDto {
    private Long boardId;
    private Long ReReplyId;
    private String comment;
}
