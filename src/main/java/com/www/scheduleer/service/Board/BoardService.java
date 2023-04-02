package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.controller.dto.board.BoardDetailDto;
import com.www.scheduleer.controller.dto.board.BoardResponseDto;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import com.www.scheduleer.controller.dto.board.BoardUpdateDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final ReplyService replyService;

    @Transactional
    public Long save(BoardSaveDto boardSaveDto, Member writer) {
        return boardRepository.save(Board.createEntity(boardSaveDto, writer)).getId();
    }

    public List<BoardResponseDto> getBoardList(int sort) {
        List<Board> boardList = boardRepository.findAllBy();
        List<BoardResponseDto> responseDto = new ArrayList<>();
        if (boardList.size() > 0) {
            boardList.forEach(data -> {
                responseDto.add(
                        BoardResponseDto.builder()
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

        List<BoardResponseDto> result = new ArrayList<>();
        switch (sort) {
            case 0 ->
                    responseDto.stream().sorted(Comparator.comparing(BoardResponseDto::getRegDate).reversed()).forEach(result::add);
            case 1 ->
                    responseDto.stream().sorted(Comparator.comparing(BoardResponseDto::getViews).reversed()).forEach(result::add);
        }
        return result;
    }

    @Transactional
    public List<Board> findBoardInfoByWriterEmail(String email) {
        return boardRepository.findBoardByWriter_Email(email);
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
}
