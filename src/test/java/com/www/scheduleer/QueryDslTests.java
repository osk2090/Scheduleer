package com.www.scheduleer;

import com.www.scheduleer.Repository.BoardRepository;
import com.www.scheduleer.Repository.QueryDsl.BoardRepositorySupport;
import com.www.scheduleer.Repository.QueryDsl.MemberRepositorySupport;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.OrderCondition;
import com.www.scheduleer.service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@SpringBootTest
@Log4j2
@Transactional
@Rollback(value = false)
@RequiredArgsConstructor
public class QueryDslTests {

    @Autowired
    private MemberRepositorySupport memberRepositorySupport;

    @Autowired
    private BoardRepositorySupport boardRepositorySupport;

    @Autowired
    private BoardService boardService;

    @Test
        // QueryDSL Test
    void useQueryDSLTest() {
        List<Member> members = memberRepositorySupport.members("케인인님");
        members.forEach(item -> {
            System.out.println(item.getEmail());
        });
    }

    @Test
    @DisplayName("cursor type pagination 1")
    void pagingTest1() {
        Long cursor = 0L;

        if (cursor > 0L) {
            List<Board> boards = boardRepositorySupport.boardsAll();
            System.out.println(boards);
        }
    }

    @Test
    @DisplayName("cursor type pagination 2")
    void pagingTest2() {
        Long cursor = 1L;

        if (cursor > 0L) {
            List<Board> boards = boardRepositorySupport.boards(cursor, 5, null, OrderCondition.VIEW);

            if (!boards.isEmpty()) {
                boards.forEach(i -> {
                    System.out.println(i.getId() + ":" + i.getTitle() + ":" + i.getContent());
                });
            }
        }
    }

    @Test
    @DisplayName("커서 방식의 페이지네이션 테스트")
    void cursorPagingTest() {

        List<Member> members = memberRepositorySupport.members("케인인님");
        Member member = null;
        if (!members.isEmpty()) {
            member = members.get(0);
        }

        for (int i = 0; i < 1000; i++) {
            BoardSaveDto boardSaveDto = new BoardSaveDto("케인인님과 타지리와의 " + i + " 차전", i + "판만!");

            boardService.save(boardSaveDto, member);
        }

        List<Board> boards = boardRepositorySupport.boardsAll();
        System.out.println("total count: " + boards.size());

    }
}
