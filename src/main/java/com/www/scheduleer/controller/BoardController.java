package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.*;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Board.BoardService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private final MemberService memberService;

    @GetMapping("/list")
    public BoardPageDto getBoardList(@RequestParam("sort") int sort,
                                           @RequestParam(required = false) Long id,
                                           @RequestParam(required = false) int limit) {
        return boardService.getBoardList(sort, id, limit);
    }

    @PostMapping("/add")
    public Long addBoard(BoardSaveDto boardSaveDto, @CurrentMember Member member) {
        return boardService.save(boardSaveDto, member);
    }

    @GetMapping("/{id}")
    public BoardDetailDto detail(@PathVariable("id") Long id,@CurrentMember Member member) {
        return boardService.findBoardById(id,member);
    }

    @PatchMapping("/update")
    public Long edit(BoardUpdateDto boardUpdateDto,@CurrentMember Member member) {
        return boardService.updateBoard(boardUpdateDto,member);
    }

}
