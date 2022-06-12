package com.www.scheduleer.web.dto.member;

import com.www.scheduleer.web.domain.MemberInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MemberInfoDto implements Serializable {
    private String name;
    private String email;
    private String password;
    private String picture;
    private String auth;

    public MemberInfoDto(MemberInfo memberInfo) {
        this.name = memberInfo.getName();
        this.email = memberInfo.getEmail();
        this.password = memberInfo.getPassword();
        this.picture = memberInfo.getPicture();
        this.auth = memberInfo.getAuth();
    }
}
