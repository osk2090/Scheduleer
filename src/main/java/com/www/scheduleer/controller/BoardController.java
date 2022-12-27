package com.www.scheduleer.controller;

import com.www.scheduleer.domain.Board;
import com.www.scheduleer.controller.dto.board.BoardSaveRequestDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @PostMapping("/board")
    public String addBoard(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal Member member) {
        boardService.saveAlgorithm(member, memberService, boardService, boardSaveRequestDto);
        return "redirect:/main";
    }

//    @GetMapping("/main")
//    public String list(Model model,  @AuthenticationPrincipal MemberDto memberDto) {
//        boardService.loginInfo(memberDto, model);
//        List<Board> boardList = boardService.getBoardList();
//        model.addAttribute("boardList", boardList);
//        return "/main";
//    }

//    @GetMapping("/board/detail/{id}")
//    public String detail(Model model, @PathVariable("id") Long boardId, @AuthenticationPrincipal MemberDto memberDto) {
//        boardService.loginInfo(memberDto, model);
//        model.addAttribute("boardDetail", boardService.findBoardById(boardId).get());
//        return "/board/detail";
//    }

    @GetMapping("/board/update/{id}")
    public String edit(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("board", boardService.findBoardById(boardId).get());
        return "/board/update";
    }

    @PutMapping("/board/update/{id}")
    @Transactional
    public String update(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal Member member) {
        boardService.saveAlgorithm(member, memberService, boardService, boardSaveRequestDto);
        return "redirect:/main";
    }

//    @GetMapping("/board/list")
//    public String myBoardList(@RequestBody MemberInfo memberInfo, Model model) {
//        model.addAttribute("boardList", boardService.findBoardInfoByWriter(memberInfo));
//        return "/member/info";
//    }
}
