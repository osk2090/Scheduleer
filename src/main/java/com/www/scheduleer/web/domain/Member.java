package com.www.scheduleer.web.domain;

import com.www.scheduleer.web.dto.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "이름은 필수값입니다.")
    private String name;

    @Column(unique = true, name = "email")
    @NotBlank(message = "이메일은 필수값입니다.")
    private String email;

    @Column(name = "password")
//    @NotBlank(message = "비밀번호는 필수값입니다.")
    private String password;

    @Column(name = "picture")
    private String picture;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
    @Column(name = "auth")
    @Enumerated(EnumType.STRING)
    private Auth auth;
    @Builder
    public Member(String name, String email, String password, String picture, Type type, Auth auth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.type = type;
        this.auth = auth;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
