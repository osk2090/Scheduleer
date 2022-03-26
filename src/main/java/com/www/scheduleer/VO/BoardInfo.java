package com.www.scheduleer.VO;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String writer;

    @CreationTimestamp
    private Date regDate;//생성 날짜

    @UpdateTimestamp
    private Date updateDate;//업데이트 날짜

    private Integer checkStar;//별표유무

    @Builder
    public BoardInfo(String title, String content, String writer, Integer checkStar) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.checkStar = checkStar;
    }
}
