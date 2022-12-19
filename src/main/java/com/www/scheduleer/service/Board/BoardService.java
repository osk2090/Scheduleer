package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.controller.dto.board.BoardSaveRequestDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
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

    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Transactional
    public Optional<Board> findBoardInfoByWriter(Member member) {
        return boardRepository.findBoardInfoByWriter(member);
    }

    public Optional<Board> findBoardById(Long boardId) {
        return boardRepository.findBoardInfoById(boardId);
    }

//    public MemberDto getLoginGoogle() {
//        return (MemberDto) httpSession.getAttribute("member");
//    }
//
//    public void loginInfo(@LoginUser MemberDto memberDto, Model model) {
//        if (memberDto == null) {
//            if (getLoginGoogle() != null) {
//                model.addAttribute("member", getLoginGoogle());
//            }
//        } else {
//            model.addAttribute("member", memberDto);
//        }
//    }

    public void saveAlgorithm(Member member, MemberService memberService, BoardService boardService, BoardSaveRequestDto boardSaveRequestDto) {
//        if (member == null) {
//            Optional<Member> loginGoogleInfo = memberService.findMemberInfoFromMemberInfoDTO(boardService.getLoginGoogle().getEmail());
//            if (loginGoogleInfo.isPresent()) {
//                boardService.save(boardSaveRequestDto, loginGoogleInfo.get());
//            }
//        } else {
//            boardService.save(boardSaveRequestDto, member);
//        }
    }
}
