package com.www.scheduleer.controller.dto.member;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MemberToReplyDto {
    private final String nickname;
    private final String picture;
}
