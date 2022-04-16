package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardInfo boardInfo, MemberInfo writer) {
        return boardRepository.save(BoardInfo.builder()
                .title(boardInfo.getTitle())
                .content(boardInfo.getContent())
                .writer(writer)
                .checkStar(boardInfo.getCheckStar()).build()).getId();
    }

    @Transactional
    public void update(Long id, Boolean yn) {
        BoardInfo boardInfo = findBoardById(id);
        boardInfo.setCheckStar(yn);
        boardRepository.save(boardInfo);
    }

    public List<BoardInfo> getBoardList() {
        return boardRepository.findAll();
    }

    public List<BoardInfo> findBoardByWriter(String writer) {
        return boardRepository.findBoardInfoByWriter(writer);
    }

    public BoardInfo findBoardById(Long boardId) {
        return boardRepository.findBoardInfoById(boardId);
    }
}
