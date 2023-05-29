package com.www.scheduleer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.www.scheduleer.Repository.QueryDsl.BoardRepositorySupport;
import com.www.scheduleer.Repository.QueryDsl.MemberRepositorySupport;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.QMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Log4j2
@RequiredArgsConstructor
public class QueryDslTests {

    @Autowired
    private MemberRepositorySupport memberRepositorySupport;

    @Autowired
    BoardRepositorySupport boardRepositorySupport;


    @Test // QueryDSL Test
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
            List<Board> boards = boardRepositorySupport.boards();
            System.out.println(boards);
        }
    }

    @Test
    @DisplayName("cursor type pagination 2")
    void pagingTest2() {
        Long cursor = 1L;

//        if (cursor > 0L) {
//            List<Board> boards = boardRepositorySupport.boards();
//            if (!boards.isEmpty()) {
//                boards.forEach(i -> {
//                    System.out.println(i.getId()+":"+i.getTitle()+":"+i.getContent());
//                });
//            }
//        }

        if (cursor > 0L) {
            List<Board> boards = boardRepositorySupport.pagingBoards(cursor);

            if (!boards.isEmpty()) {
                boards.forEach(i -> {
                    System.out.println(i.getId()+":"+i.getTitle()+":"+i.getContent());
                });
            }
        }
    }
}
