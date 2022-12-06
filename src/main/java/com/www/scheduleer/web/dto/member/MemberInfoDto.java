package com.www.scheduleer.web.dto.member;

import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.domain.Type;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private String name;
    private String email;
    private String password;
    private String picture;
    private Auth auth;

    private Type type;

    public MemberInfoDto(MemberInfo memberInfo) {
        this.name = memberInfo.getName();
        this.email = memberInfo.getEmail();
        this.password = memberInfo.getPassword();
        this.picture = memberInfo.getPicture();
        this.auth = memberInfo.getAuth();
        this.type = memberInfo.getType();
    }
}
