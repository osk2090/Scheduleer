package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

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
    public Member(String name, String email, String password, String picture, Type type, String auth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.type = Type.GENERAL;
        this.auth = "ROLE_USER";
    }

    public static Member testCreate(String email, String pw) {
        return Member.builder()
                .email(email)
                .password(pw)
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
