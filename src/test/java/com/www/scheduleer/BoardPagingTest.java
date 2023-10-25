package com.www.scheduleer;

import com.www.scheduleer.Repository.QueryDsl.BoardRepositorySupport;
import com.www.scheduleer.Repository.QueryDsl.MemberRepositorySupport;
import com.www.scheduleer.controller.dto.board.BoardSaveDto;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.enums.OrderType;
import com.www.scheduleer.service.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
@Rollback(value = false)
public class BoardPagingTest {
    @Autowired
    private MemberRepositorySupport memberRepositorySupport;

    @Autowired
    private BoardRepositorySupport boardRepositorySupport;
    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("커서 방식의 페이지네이션 테스트를 위한 더미 데이터 생성")
    void makeBoardData() {

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

    @Test
    @DisplayName("커서 방식의 페이지네이션 테스트 - 1페이지")
    void cursorPagingTest1() {
        List<Board> boards = boardRepositorySupport.boards(null, 5, null, OrderType.REG);
        if (!boards.isEmpty()) {
            System.out.println("page count: " + boards.size());

            if (!boards.isEmpty()) {
                boards.forEach(i -> {
                    System.out.println(i.getId() + ":" + i.getTitle() + ":" + i.getContent());
                });
            }
        }
    }

    @Test
    @DisplayName("커서 방식의 페이지네이션 테스트 - 2페이지")
    void cursorPagingTest2() {
        List<Board> boards = boardRepositorySupport.boards(5L, 5, null, OrderType.REG);
        if (!boards.isEmpty()) {
            System.out.println("page count: " + boards.size());

            if (!boards.isEmpty()) {
                boards.forEach(i -> {
                    System.out.println(i.getId() + ":" + i.getTitle() + ":" + i.getContent());
                });
            }
        }
    }
}
