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
    }

    public List<BoardInfo> getBoardList() {
        return boardRepository.findAll();
    }

    @Transactional
    public Optional<BoardInfo> findBoardInfoByWriter(MemberInfo memberInfo) {
        return boardRepository.findBoardInfoByWriter(memberInfo);
    }

    public Optional<BoardInfo> findBoardById(Long boardId) {
        return boardRepository.findBoardInfoById(boardId);
    }
}
