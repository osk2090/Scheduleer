package com.www.scheduleer.web.dto.board;

import com.www.scheduleer.web.domain.BoardInfo;
import com.www.scheduleer.web.domain.MemberInfo;
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
    private MemberInfo memberInfo;
    private Boolean checkStar;
    private LocalDateTime regDate;
    private LocalDateTime updatedDate;

    public BoardInfo toEntity() {
        return BoardInfo.builder()
                .id(id)
                .title(title)
                .content(content)
                .writer(memberInfo)
                .checkStar(checkStar).build();
    }

    @Builder
    public BoardSaveRequestDto(Long id, String title, String content, MemberInfo memberInfo, Boolean checkStar, LocalDateTime regDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.memberInfo = memberInfo;
        this.checkStar = checkStar;
        this.regDate = regDate;
        this.updatedDate = updatedDate;
    }
}
