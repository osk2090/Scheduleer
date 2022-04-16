package com.www.scheduleer.controller;

import com.www.scheduleer.VO.BoardInfo;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/detail")
    public String detail(Model model, Long boardId) {
        model.addAttribute("boardDetail", boardService.findBoardById(boardId));
        return "/board/detail";
    }

    @PutMapping("/update/{id}/{checkStar}")
    public String edit(@PathVariable("id") Long boardId, @PathVariable("checkStar") Boolean yn) {
        System.out.println("*****boardId = " + boardId);
        System.out.println("****yn = " + yn);

        boardService.update(boardId, yn);
        return "redirect:/main";
    }

//    @PutMapping("/update/{id}")
//    public String updateBoard(BoardInfo boardInfo, @AuthenticationPrincipal MemberInfo memberInfo) {
//        boardService.save(boardInfo, memberInfo);
//        return "redirect:/main";
//    }
}
