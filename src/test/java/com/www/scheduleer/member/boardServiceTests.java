package com.www.scheduleer.member;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.controller.dto.board.BoardResponseDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.service.Board.BoardService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@Log4j2
public class boardServiceTests {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    @DisplayName("board/list-- 메인페이지")
    public void uploadProfile() throws IOException {
        long stime = System.currentTimeMillis();

        List<Board> boardList = boardRepository.findAllBy();
        List<BoardResponseDto> responseDto = new ArrayList<>();
        if (boardList.size() > 0) {
            boardList.forEach(data -> {
                responseDto.add(
                        BoardResponseDto.builder()
                                .title(data.getTitle())
                                .nickName(data.getWriter().getNickName())
                                .picture(data.getWriter().getPicture())
                                .views(data.getViews())
                                .isCheck(data.getCheckStar())
                                .regDate(data.getRegDate())
                                .build()
                );
            });
        }
        System.out.println("--생성일자별--");
        responseDto.stream().sorted(Comparator.comparing(BoardResponseDto::getRegDate).reversed()).forEach(System.out::println);
        System.out.println("----");

        System.out.println("--조회수별--");
        responseDto.stream().sorted(Comparator.comparing(BoardResponseDto::getViews).reversed()).forEach(System.out::println);
        System.out.println("----");

        System.out.println("소요시간:"+(System.currentTimeMillis()-stime)+"ms");
    }
}
