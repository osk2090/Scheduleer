package com.www.scheduleer.VO.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoDto {
    private String name;
    private String email;
    private String password;

    private String auth;
}
