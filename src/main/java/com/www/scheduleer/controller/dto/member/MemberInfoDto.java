package com.www.scheduleer.controller.dto.member;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberInfoDto {
    private String name;
    private String email;
    private String password;
    private String picture;
    private List<BoardInfoDto> boardInfoDtoList;
}
