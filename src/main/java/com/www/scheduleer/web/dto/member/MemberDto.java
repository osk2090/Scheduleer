package com.www.scheduleer.web.dto.member;

import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.domain.Type;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String picture;
    private Auth auth;

    private Type type;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public Member toEntity() {
        return Member.builder().name(name).email(email).password(password).picture(picture).auth(Auth.USER).type(Type.GENERAL).build();
    }

    @Builder
    public MemberDto(Long id, String name, String email, String password, String picture, Auth auth, Type type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.auth = auth;
        this.type = type;
    }
}
