package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.web.domain.BoardInfo;
import com.www.scheduleer.web.dto.board.BoardSaveRequestDto;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final HttpSession httpSession;

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

    public MemberInfoDto getLoginGoogle() {
        return (MemberInfoDto) httpSession.getAttribute("member");
    }

    public void loginInfo(MemberInfo memberInfo, Model model) {
        if (memberInfo == null) {
            if (getLoginGoogle() != null) {
                model.addAttribute("member", getLoginGoogle());
            }
        } else {
            model.addAttribute("member", memberInfo);
        }
    }

    public void saveAlgorithm(MemberInfo memberInfo, MemberService memberService, BoardService boardService, BoardSaveRequestDto boardSaveRequestDto) {
        if (memberInfo == null) {
            Optional<MemberInfo> loginGoogleInfo = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail());
            if (loginGoogleInfo.isPresent()) {
                boardService.save(boardSaveRequestDto, loginGoogleInfo.get());
            }
        } else {
            boardService.save(boardSaveRequestDto, memberInfo);
        }
    }
}
