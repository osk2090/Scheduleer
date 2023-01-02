package com.www.scheduleer.controller.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
public class MemberInfo {
    private String name;
    private String email;
    private String password;
    private String picture;
    private List<BoardInfo> boardInfoList;
}
