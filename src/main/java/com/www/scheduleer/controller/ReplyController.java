package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.ReplyService;
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

    @PostMapping("/add")
    public Long addReply(ReplySaveDto replySaveDto, @CurrentMember Member member) {
        producer.sendMessage(member.getNickName()+" 님이 댓글을 등록하였습니다!");

        return replyService.save(replySaveDto, member);
    }
}
