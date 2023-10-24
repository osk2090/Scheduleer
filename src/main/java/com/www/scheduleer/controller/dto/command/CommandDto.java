package com.www.scheduleer.controller.dto.command;

import com.www.scheduleer.domain.enums.CommandType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommandDto {
    private CommandType commandType; // consumer에서 실행할 명령
    private LocalDateTime localDateTime;
    private KakfaObj data; // 카프카에 전달할 데이터
}
