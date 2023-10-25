package com.www.scheduleer.service.board;

import com.www.scheduleer.Repository.ReplyRepository;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.controller.dto.command.CommandDto;
import com.www.scheduleer.controller.dto.command.DataType;
import com.www.scheduleer.controller.dto.command.KakfaObj;
import com.www.scheduleer.controller.dto.notification.NotificationSaveDto;
import com.www.scheduleer.controller.dto.reply.ReplyResponseDto;
import com.www.scheduleer.controller.dto.board.ReplySaveDto;
import com.www.scheduleer.controller.dto.member.MemberToReplyDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.Reply;
import com.www.scheduleer.domain.enums.CommandType;
import com.www.scheduleer.service.kafka.KafkaProducer;
import com.www.scheduleer.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final NotificationService notiService;
    private final KafkaProducer producer;

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

    public Long save(ReplySaveDto replySaveDto, Member member) {
        return replyRepository.save(Reply.createReply(replySaveDto, member)).getId();
    }

    @Transactional
    public void sendNotification(ReplySaveDto replySaveDto, Board board, Member member) {

        //게시글 작성자와 댓글 작성자가 다를때만 알림 전송!
        if (!board.getWriter().getId().equals(member.getId())) {
            StringBuilder msg = new StringBuilder(member.getNickName() + " 님이 " + board.getTitle() + " 글에 댓글을 등록하였습니다!");

            // reply db 저장
            this.save(replySaveDto, board.getWriter());
            // noti db 저장
            Long notificationId = notiService.save(NotificationSaveDto.builder()
                    .message(String.valueOf(msg))
                    .type(DataType.REPLY)
                    .build(), board.getWriter());

            // kafka 이벤트 메시지 전송
            producer.sendMessage(CommandDto.builder().
                    commandType(CommandType.NOTI).
                    localDateTime(LocalDateTime.now()).
                    data(KakfaObj.builder()
                            .type(DataType.REPLY)
                            .toId(board.getWriter().getId())
                            .notificationId(notificationId)
                            .build()
                    ).build()
            );
        }
    }
}
