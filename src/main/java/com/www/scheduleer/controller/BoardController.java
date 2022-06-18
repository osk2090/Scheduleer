package com.www.scheduleer.controller;

import com.www.scheduleer.web.domain.BoardInfo;
import com.www.scheduleer.web.dto.board.BoardSaveRequestDto;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @PostMapping("/board")
    public String addBoard(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.saveAlgorithm(memberInfo, memberService, boardService, boardSaveRequestDto);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String list(Model model, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.loginInfo(memberInfo, model);
        List<BoardInfo> boardInfoList = boardService.getBoardList();
        model.addAttribute("boardList", boardInfoList);
        return "/main";
    }

    @GetMapping("/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long boardId, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.loginInfo(memberInfo, model);
        model.addAttribute("boardDetail", boardService.findBoardById(boardId).get());
        return "/board/detail";
    }

    @GetMapping("/board/update/{id}")
    public String edit(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("board", boardService.findBoardById(boardId).get());
        return "/board/update";
    }

    @PutMapping("/board/update/{id}")
    @Transactional
    public String update(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.saveAlgorithm(memberInfo, memberService, boardService, boardSaveRequestDto);
        return "redirect:/main";
    }

//    @GetMapping("/board/list")
//    public String myBoardList(@RequestBody MemberInfo memberInfo, Model model) {
//        model.addAttribute("boardList", boardService.findBoardInfoByWriter(memberInfo));
//        return "/member/info";
//    }
}
