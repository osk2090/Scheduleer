package com.www.scheduleer.service.board;

import com.www.scheduleer.Repository.ReplyRepository;
import com.www.scheduleer.controller.dto.reply.ReplyResponseDto;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.controller.dto.member.MemberToReplyDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.Reply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<ReplyResponseDto> getReReplyFunc(List<ReplyResponseDto> reReplyArr, Long replyId) {
        List<ReplyResponseDto> resultReReply = new ArrayList<>();
        reReplyArr.forEach(data -> {
            if (data.getReReplyId().equals(replyId)) {
                resultReReply.add(data);
            }
        });
        List<ReplyResponseDto> result = new ArrayList<>();
        resultReReply.stream().sorted(Comparator.comparing(ReplyResponseDto::getRegDate)).forEach(result::add);
        return result;
    }

    public List<ReplyResponseDto> findReplies(Long boardId) {
        List<Reply> replies = replyRepository.findByBoardId(boardId);
        List<ReplyResponseDto> replyList = new ArrayList<>();
        List<ReplyResponseDto> reReplyList = new ArrayList<>();
        replies.forEach(data -> {
            if (data.getReReplyId() != null) {
                reReplyList.add(ReplyResponseDto.builder()
                        .id(data.getId())
                        .boardId(data.getBoardId())
                        .reReplyId(data.getReReplyId())
                        .comment(data.getComment())
                        .memberToReplyDto(new MemberToReplyDto(data.getWriter().getNickName(), data.getWriter().getPicture()))
                        .regDate(data.getRegDate())
                        .build());
            }
        });


        replies.forEach(data -> {
            if (data.getReReplyId() == null) {
                replyList.add(ReplyResponseDto.builder()
                        .id(data.getId())
                        .boardId(data.getBoardId())
                        .reReplyId(data.getReReplyId())
                        .comment(data.getComment())
                        .memberToReplyDto(new MemberToReplyDto(data.getWriter().getNickName(), data.getWriter().getPicture()))
                        .reReplyResponse(getReReplyFunc(reReplyList, data.getId()))
                        .regDate(data.getRegDate())
                        .build());
            }
        });

        List<ReplyResponseDto> result = new ArrayList<>();
        replyList.stream().sorted(Comparator.comparing(ReplyResponseDto::getRegDate)).forEach(result::add);
        return result;
    }

    @Transactional
    public Long save(ReplySaveDto replySaveDto, Member member) {
        return replyRepository.save(Reply.createReply(replySaveDto, member)).getId();
    }

}
