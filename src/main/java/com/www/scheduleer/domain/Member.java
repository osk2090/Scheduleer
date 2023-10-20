package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import com.www.scheduleer.controller.dto.member.SignUpDto;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Entity(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    private String nickName;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "picture")
    private String picture;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "auth")
    private String auth;
    @Builder
    public Member(String name, String nickName, String email, String password, String picture, Type type, String auth) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.type = Type.GENERAL;
        this.auth = "ROLE_USER";
    }

    public static Member createEntity(SignUpDto signUpDto, String picture) {
        return Member.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .name(signUpDto.getName())
                .nickName(signUpDto.getNickName())
                .picture(picture)
                .auth(Auth.ROLE_USER.toString())
                .type(Type.GENERAL)
                .build();
    }

    public List<String> getAuthList() {
        if (auth.length() > 0) {
            return Arrays.asList(auth.split(","));
        }
        return new ArrayList<>();
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
}
