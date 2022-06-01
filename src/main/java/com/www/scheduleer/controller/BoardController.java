package com.www.scheduleer.controller;

import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.BoardSaveRequestDto;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.VO.security.MemberInfoDto;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    private final HttpSession httpSession;

    @PostMapping("/board")
    public String addBoard(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.save(boardSaveRequestDto, memberInfo);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String list(Model model, @AuthenticationPrincipal MemberInfo memberInfoDto) {
        MemberInfoDto loginGoogle = (MemberInfoDto) httpSession.getAttribute("member");
        if (memberInfoDto == null) {
            if (loginGoogle != null) {
                model.addAttribute("memberName", loginGoogle);
            }
        } else {
            System.out.println(memberInfoDto.getName() + "!!!");
            model.addAttribute("memberName", memberInfoDto);
        }

        List<BoardInfo> boardInfoList = boardService.getBoardList();
        model.addAttribute("boardList", boardInfoList);

        return "/main";
    }

    @GetMapping("/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("boardDetail", boardService.findBoardById(boardId).get());
        return "/board/detail";
    }

    @GetMapping("/board/update/{id}")
    public String edit(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("board", boardService.findBoardById(boardId).get());
        return "/board/update";
    }

    @PutMapping("/board/update/{id}")
    public String update(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        if (boardSaveRequestDto.getCheckStar() == null) {
            boardSaveRequestDto.setCheckStar(false);
            System.out.println("----" + boardSaveRequestDto.getCheckStar());
        }
        boardService.save(boardSaveRequestDto, memberInfo);
        return "redirect:/main";
    }

//    @GetMapping("/board/list")
//    public String myBoardList(@RequestBody MemberInfo memberInfo, Model model) {
//        model.addAttribute("boardList", boardService.findBoardInfoByWriter(memberInfo));
//        return "/member/info";
//    }
}
