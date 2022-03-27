package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.VO.BoardInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardInfo boardInfo, String writer) {
        return boardRepository.save(BoardInfo.builder()
                .title(boardInfo.getTitle())
                .content(boardInfo.getContent())
                .writer(writer)
                .checkStar(boardInfo.getCheckStar()).build()).getId();
    }

    public List<BoardInfo> getBoardList() {
        return boardRepository.findAll();
    }

    public List<BoardInfo> findBoardByWriter(String writer) {
        return boardRepository.findBoardInfoByWriter(writer);
    }
}
