package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.board.BoardService;
import com.www.scheduleer.service.board.ReplyService;
import com.www.scheduleer.service.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final KafkaProducer producer;
    private final BoardService boardService;

    @PostMapping("/add")
    public Long addReply(ReplySaveDto replySaveDto, @CurrentMember Member member) {
        Board board = boardService.findByBoardId(replySaveDto.getBoardId());
        producer.sendMessage(board.getWriter().getId(), member.getNickName() + " 님이 " + board.getTitle() + " 글에 댓글을 등록하였습니다!");

        return replyService.save(replySaveDto, member);
    }
}
