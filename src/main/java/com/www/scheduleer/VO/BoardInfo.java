package com.www.scheduleer.VO;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob//대용량
    private String content;

    @CreationTimestamp
    private Date regDate;//생성 날짜

    @UpdateTimestamp
    private Date updateDate;//업데이트 날짜

    private Integer checkStar;//별표유무

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id")
    private MemberInfo writer;

    @Builder
    public BoardInfo(String title, String content, Integer checkStar, MemberInfo writer) {
        this.title = title;
        this.content = content;
        this.checkStar = checkStar;
        this.writer = writer;
    }
}
