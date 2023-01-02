package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.controller.dto.board.BoardResponseDto;
import com.www.scheduleer.controller.dto.board.BoardSaveRequestDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final HttpSession httpSession;

    @Transactional
    public Long save(BoardSaveRequestDto boardSaveRequestDto, Member writer) {
        boardSaveRequestDto.setMember(writer);
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
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

    public Optional<Board> findBoardById(Long boardId) {
        return boardRepository.findBoardInfoById(boardId);
    }

}
