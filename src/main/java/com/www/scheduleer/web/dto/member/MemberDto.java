package com.www.scheduleer.web.dto.member;

import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.domain.Type;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private String name;
    private String email;
    private String password;
    private String picture;
    private Auth auth;

    private Type type;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public Member toEntity() {
        return Member.builder().name(name).email(email).password(password).picture(picture).auth(Auth.USER).type(type).build();
    }

    @Builder
    public MemberDto(String name, String email, String password, String picture, Auth auth, Type type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.auth = auth;
        this.type = type;
    }
}
