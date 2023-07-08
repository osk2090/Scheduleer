package com.www.scheduleer.controller.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardUpdateDto {
    private Long boardId;
    private String content;
    private Boolean checkStar;
}
