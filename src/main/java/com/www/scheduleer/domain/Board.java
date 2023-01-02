package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import lombok.*;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.Null;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob//대용량
    @Column(nullable = false)
    private String content;

    private Boolean checkStar;//별표유무

    @Column(nullable = false)
    private int views;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
//    @NotNull
    private Member writer;

    @Builder
    public Board(Long id, String title, String content, Boolean checkStar, Member writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.checkStar = checkStar;
        this.writer = writer;
    }
}
