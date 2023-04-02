package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/add")
    public Long addReply(ReplySaveDto replySaveDto, @CurrentMember Member member) {
        return replyService.save(replySaveDto, member);
    }
}
