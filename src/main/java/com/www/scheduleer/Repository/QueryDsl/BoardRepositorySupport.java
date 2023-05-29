package com.www.scheduleer.Repository.QueryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.www.scheduleer.domain.QBoard.board;

@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public BoardRepositorySupport(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    public List<Board> boards() {
        return queryFactory.selectFrom(board).fetch();
    }

    public List<Board> pagingBoards(Long id) {
        return queryFactory.selectFrom(board).where(board.id.gt(id)).fetch();
    }
}
