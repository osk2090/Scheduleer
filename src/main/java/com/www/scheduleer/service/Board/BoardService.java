package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.controller.dto.board.BoardDetailDto;
import com.www.scheduleer.controller.dto.board.BoardResponseDto;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    public BoardDetailDto findBoardById(Long boardId) {
        Optional<Board> board = boardRepository.findBoardInfoById(boardId);
        Board b = null;
        BoardDetailDto boardDetailDto = null;
        if (board.isPresent()) {
            b = board.get();

            boardDetailDto = new BoardDetailDto(
                    b.getTitle(),
                    b.getWriter().getNickName(),
                    b.getWriter().getPicture(),
                    b.getViews(),
                    b.getCheckStar(),
                    b.getRegDate(),
                    replyService.findReplies(b.getId())
            );
        }
        return boardDetailDto;
    }

}
