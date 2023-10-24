package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.board.BoardService;
import com.www.scheduleer.service.board.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
@Slf4j
public class ReplyController {

    private final ReplyService replyService;
    private final BoardService boardService;

    @PostMapping("/add")
    public void addReply(ReplySaveDto replySaveDto, @CurrentMember Member member) {
        Board board = boardService.findByBoardId(replySaveDto.getBoardId());

        replyService.sendNotification(replySaveDto, board, member);
    }
}
