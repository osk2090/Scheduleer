package com.www.scheduleer.VO;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String name;

    private String email;

    private String password;

    @CreationTimestamp
    private Date regDate;

    @UpdateTimestamp
    private Date updateDate;

    @Enumerated(EnumType.STRING)//열거형을 인덱스 번호가 아닌 문자형으로 받기
    private MemberRole memberRole;

    @Builder
    public Member(String userId, String name, String email, String password, MemberRole memberRole) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
    }
}
