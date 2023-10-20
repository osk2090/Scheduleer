package com.www.scheduleer.controller;

import com.www.scheduleer.config.annotation.CurrentMember;
import com.www.scheduleer.controller.dto.board.*;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.board.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "메인 스케줄 호출", description = "유저들의 스케줄 리스트", tags = {"Board Controller"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = BoardPageDto.class)))
    })
    @GetMapping("/list")
    public BoardPageDto getBoardList(@Parameter(description = "정렬 기준 (0: 생성일자순, 1: 조회수순)", required = true)
                                     @RequestParam(defaultValue = "0")  int sort,
                                     @RequestParam(required = false) Long id,
                                     @RequestParam(defaultValue = "10") int limit) {

        return boardService.getBoardList(sort, id, null, limit);
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
