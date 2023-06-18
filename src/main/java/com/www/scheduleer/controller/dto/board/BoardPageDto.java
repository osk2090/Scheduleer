package com.www.scheduleer.controller.dto.board;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardPageDto {
    List<BoardResponseDto> boardResponseDto;
    Long lastIndex;
}
