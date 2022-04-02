package com.www.scheduleer.VO;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    @NotNull
    private String title;

    @Lob//대용량
    @NotNull
    private String content;

    @NotNull
    private Integer checkStar;//별표유무

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    @NotNull
    private MemberInfo writer;

    @Builder
    public BoardInfo(String title, String content, Integer checkStar, MemberInfo writer) {
        this.title = title;
        this.content = content;
        this.checkStar = checkStar;
        this.writer = writer;
    }
}
