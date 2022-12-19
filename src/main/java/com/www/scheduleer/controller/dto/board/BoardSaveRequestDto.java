package com.www.scheduleer.controller.dto.board;

import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardSaveRequestDto {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private Boolean checkStar;
    private LocalDateTime regDate;
    private LocalDateTime updatedDate;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(member)
                .checkStar(checkStar).build();
    }

    @Builder
    public BoardSaveRequestDto(Long id, String title, String content, Member member, Boolean checkStar, LocalDateTime regDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.checkStar = checkStar;
        this.regDate = regDate;
        this.updatedDate = updatedDate;
    }
}
