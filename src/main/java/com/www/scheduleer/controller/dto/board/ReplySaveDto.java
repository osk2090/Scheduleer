package com.www.scheduleer.controller.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class ReplySaveDto {
    @NotBlank(message = "NOT_EMPTY_BOARD_ID")
    private Long boardId;
    private Long ReReplyId;
    @NotBlank(message = "NOT_EMPTY_COMMENT")
    private String comment;
}
