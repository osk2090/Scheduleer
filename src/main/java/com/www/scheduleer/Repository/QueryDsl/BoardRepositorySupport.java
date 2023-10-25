package com.www.scheduleer.Repository.QueryDsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.enums.OrderType;
import com.www.scheduleer.domain.QBoard;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class BoardRepositorySupport extends QuerydslRepositorySupport {
    QBoard board = QBoard.board;

    private final JPAQueryFactory queryFactory;


    public BoardRepositorySupport(JPAQueryFactory queryFactory) {
        super(Board.class);
        this.queryFactory = queryFactory;
    }

    public List<Board> boardsAll() {
        return queryFactory.selectFrom(board).fetch();
    }

    private BooleanExpression gtId(Long id) {
        if (id == null) {
            return null;
        }

        return board.id.gt(id);
    }

    private BooleanExpression eqMember(Member member) {
        if (member == null) {
            return null;
        }

        return board.writer.eq(member);
    }

    public List<Board> boards(Long id, int limit, Member member, OrderType orderType) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderType);

        return queryFactory.selectFrom(board)
                .where(gtId(id), eqMember(member))
                .orderBy(orderSpecifiers)
                .limit(limit)
                .fetch();
    }
    public OrderSpecifier[] createOrderSpecifier(OrderType orderType) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        if (Objects.isNull(orderType) || orderType.equals(OrderType.REG)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.regDate));
        } else if (orderType.equals(OrderType.VIEW)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.views));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    public void insertBoard(String title, String content, int views, boolean checkStar, Member member) {
        queryFactory
                .insert(board)
                .set(board.title, title)
                .set(board.content, content)
                .set(board.views, views)
                .set(board.checkStar, checkStar)
                .set(board.writer, member)
                .execute();
    }

}
