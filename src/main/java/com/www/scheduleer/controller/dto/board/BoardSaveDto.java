package com.www.scheduleer.controller.dto.board;

import com.www.scheduleer.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BoardSaveDto {
    private String title;
    private String content;
}
