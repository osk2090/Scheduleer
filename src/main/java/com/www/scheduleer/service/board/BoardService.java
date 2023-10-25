package com.www.scheduleer.service.board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.QueryDsl.BoardRepositorySupport;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.controller.dto.board.*;
import com.www.scheduleer.controller.dto.command.DataType;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.enums.OrderType;
import com.www.scheduleer.service.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final KafkaProducer producer;

    private final ReplyService replyService;
    private final BoardRepositorySupport boardRepositorySupport;

    @Transactional
    public Long save(BoardSaveDto boardSaveDto, Member writer) {
        Long id = boardRepository.save(Board.createEntity(boardSaveDto, writer)).getId();
        StringBuilder msg = new StringBuilder(writer.getNickName() + " 님이 새로운 계획을 등록하였습니다!");
        //TODO: 새로운 테이블로 구성해야될지 검토
//        producer.sendMessage(DataType.BOARD, writer.getId(), String.valueOf(msg));
        return id;
    }

    public BoardPageDto getBoardList(int sort, Long id, Member member, int limit) {
        OrderType orderType = null;
        if (sort == 0) {
            orderType = OrderType.REG;
        } else if (sort == 1) {
            orderType = OrderType.VIEW;
        }

        List<Board> boardList = boardRepositorySupport.boards(id, limit, member, orderType);
        List<BoardResponseDto> responseDto = new ArrayList<>();
        if (!boardList.isEmpty()) {
            boardList.forEach(data -> {
                responseDto.add(
                        BoardResponseDto.builder()
                                .id(data.getId())
                                .title(data.getTitle())
                                .nickName(data.getWriter().getNickName())
                                .picture(data.getWriter().getPicture())
                                .views(data.getViews())
                                .isCheck(data.getCheckStar())
                                .regDate(data.getRegDate())
                                .build()
                );
            });
        }

        return BoardPageDto.builder()
                .boardResponseDto(responseDto)
                .lastIndex(!responseDto.isEmpty() ? responseDto.get(responseDto.size() - 1).getId() : null)
                .build();
    }

    @Transactional
    public List<Board> findBoardInfoByWriter(Member member) {
        return boardRepository.findBoardByWriter(member);
    }

    public String findBoardByIdForTitle(Long boardId) {
        Optional<Board> board = boardRepository.findBoardInfoById(boardId);
        return board.map(Board::getTitle).orElse(null);
    }

    public BoardDetailDto findBoardById(Long boardId, Member member) {
        Optional<Board> board = boardRepository.findBoardInfoById(boardId);
        Board b = null;
        BoardDetailDto boardDetailDto = null;
        if (board.isPresent()) {
            b = board.get();

            boardDetailDto = BoardDetailDto.builder()
                    .title(b.getTitle())
                    .nickName(b.getWriter().getNickName())
                    .picture(b.getWriter().getPicture())
                    .views(b.getViews())
                    .isCheck(b.getCheckStar())
                    .regDate(b.getRegDate())
                    .replyResponse(replyService.findReplies(b.getId()))
                    .build();
            if (!Objects.equals(member.getEmail(), b.getWriter().getEmail())) {
                int oldViews = b.getViews();
                b.setViews(oldViews + 1);
                boardRepository.save(b);
            }
        }

        return boardDetailDto;
    }

    public Long updateBoard(BoardUpdateDto boardUpdateDto, Member member) {
        Optional<Board> board = boardRepository.findBoardInfoById(boardUpdateDto.getBoardId());

        if (board.isPresent()) {
            Board b = board.get();
            if (!Objects.equals(member.getEmail(), b.getWriter().getEmail())) {
                throw new CustomException(ErrorCode.NOT_MATCH_WRITER);
            }

            b.setContent(boardUpdateDto.getContent());
            b.setCheckStar(boardUpdateDto.getCheckStar());
            return boardRepository.save(b).getId();
        }
        return null;
    }

    public Board findByBoardId(Long boardId) {
        Optional<Board> board = boardRepository.findBoardInfoById(boardId);
        Board b = null;
        if (board.isPresent()) {
             b = board.get();
        }
        return b;
    }
}
