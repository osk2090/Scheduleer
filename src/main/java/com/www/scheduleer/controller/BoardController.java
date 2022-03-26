package com.www.scheduleer.controller;

import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board")
    public String addBoard(BoardInfo boardInfo) {
        boardService.save(boardInfo);
        return "redirect:/";
    }
}
