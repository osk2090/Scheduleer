package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import lombok.*;

import javax.persistence.*;

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
    @JoinColumn(name = "member_info_id", nullable = false)
    private Member writer;

    @Builder
    public Board(String title, String content, Boolean checkStar, int views, Member writer) {
        this.title = title;
        this.content = content;
        this.checkStar = checkStar;
        this.views = views;
        this.writer = writer;
    }

    public static Board createEntity(BoardSaveDto boardSaveDto, Member member) {
        return Board.builder().
                title(boardSaveDto.getTitle())
                .content(boardSaveDto.getContent())
                .writer(member)
                .checkStar(false)
                .views(0)
                .build();
    }

}
