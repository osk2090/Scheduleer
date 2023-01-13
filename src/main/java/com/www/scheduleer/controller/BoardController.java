package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.BoardResponseDto;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
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
    public List<BoardResponseDto> getBoardList(@RequestParam("sort") int sort) {
        return boardService.getBoardList(sort);
    }

    @PostMapping("/add")
    public Long addBoard(BoardSaveDto boardSaveDto, @CurrentMember Member member) {
        return boardService.save(boardSaveDto, member);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, @CurrentMember Member member) {
        return null;
    }

    @GetMapping("/board/update/{id}")
    public String edit(Model model, @PathVariable("id") Long boardId) {
        model.addAttribute("board", boardService.findBoardById(boardId).get());
        return "/board/update";
    }

//    @PutMapping("/board/update/{id}")
//    @Transactional
//    public String update(BoardSaveRequestDto boardSaveRequestDto, @AuthenticationPrincipal Member member) {
//    }

//    @GetMapping("/board/list")
//    public String myBoardList(@RequestBody MemberInfo memberInfo, Model model) {
//        model.addAttribute("boardList", boardService.findBoardInfoByWriter(memberInfo));
//        return "/member/info";
//    }
}
