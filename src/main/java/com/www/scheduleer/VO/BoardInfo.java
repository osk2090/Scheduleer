package com.www.scheduleer.VO;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
//    @NotNull
    private String title;

    @Lob//대용량
//    @NotNull
    private String content;

    private Boolean checkStar;//별표유무

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
//    @NotNull
    private MemberInfo writer;

    @Builder
    public BoardInfo(Long id, String title, String content, Boolean checkStar, MemberInfo writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.checkStar = checkStar;
        this.writer = writer;
    }
}
