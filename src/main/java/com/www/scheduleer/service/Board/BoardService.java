package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.BoardSaveRequestDto;
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
    public Long save(BoardSaveRequestDto boardSaveRequestDto, MemberInfo writer) {
        boardSaveRequestDto.setMemberInfo(writer);
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
//        return boardRepository.save(
//                BoardInfo.builder()
//                .id(boardInfo.getId())
//                .title(boardInfo.getTitle())
//                .content(boardInfo.getContent())
//                .writer(writer)
//                .checkStar(boardInfo.getCheckStar()).build()).getId();

    }
//    @Transactional
//    public void update(Long id, BoardInfo boardInfo) {
//        BoardInfo b = boardRepository.findBoardInfoById(id);
//        b.setTitle(boardInfo.getTitle());
//        b.setContent(boardInfo.getContent());
//        b.setCheckStar(boardInfo.getCheckStar());
//        System.out.println("저장됨!");
//        boardRepository.save(b);
//    }

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
