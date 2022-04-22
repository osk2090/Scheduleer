package com.www.scheduleer.controller;

import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.BoardSaveRequestDto;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public String addBoard(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.save(boardSaveRequestDto, memberInfo);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String list(Model model) {
        List<BoardInfo> boardInfoList = boardService.getBoardList();
        model.addAttribute("boardList", boardInfoList);
        return "/main";
    }

    @GetMapping("/board/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("boardDetail", boardService.findBoardById(boardId));
        return "/board/detail";
    }

    @GetMapping("/board/update/{id}")
    public String edit(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("board", boardService.findBoardById(boardId));
        return "/board/update";
    }

    @PutMapping("/board/update/{id}")
    public String update(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.save(boardSaveRequestDto, memberInfo);
        return "redirect:/main";
    }

}
