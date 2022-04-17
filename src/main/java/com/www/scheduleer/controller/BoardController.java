package com.www.scheduleer.controller;

import com.www.scheduleer.VO.BoardInfo;
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
    public String addBoard(BoardInfo boardInfo, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.save(boardInfo, memberInfo);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String list(Model model) {
        List<BoardInfo> boardInfoList = boardService.getBoardList();
        model.addAttribute("boardList", boardInfoList);
        return "/main";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("boardDetail", boardService.findBoardById(boardId));
        return "/board/detail";
    }

//    @GetMapping("/update/{id}")
//    public String edit(@PathVariable("id") Long boardId, Model model) {
//        BoardInfo boardInfo = boardService.findBoardById(boardId);
//    }

    @PutMapping("/update/{id}")
    public String update(BoardInfo boardInfo, @AuthenticationPrincipal MemberInfo memberInfo) {
        boardService.save(boardInfo, memberInfo);
        return "redirect:/main";
    }

}
