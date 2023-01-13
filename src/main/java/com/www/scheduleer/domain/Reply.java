package com.www.scheduleer.domain;

import com.www.scheduleer.controller.dto.BaseTimeEntity;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;
    @Column()
    private Long reReplyId;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_info_id", nullable = false)
    private Member writer;
    @Builder
    public Reply(Long boardId, Long reReplyId, String comment, Member writer) {
        this.boardId = boardId;
        this.reReplyId = reReplyId;
        this.comment = comment;
        this.writer = writer;
    }

    public static Reply createReply(ReplySaveDto replySaveDto, Member member) {
        return Reply.builder()
                .boardId(replySaveDto.getBoardId())
                .reReplyId(replySaveDto.getReReplyId())
                .comment(replySaveDto.getComment())
                .writer(member)
                .build();
    }

}
