package com.www.scheduleer.Repository.QueryDsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.OrderCondition;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    //private OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {
    //
    //        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
    //
    //        if(Objects.isNull(orderCondition)){
    //            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.name));
    //        }else if(orderCondition.equals(OrderCondition.AGE)){
    //            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.age));
    //        }else{
    //            orderSpecifiers.add(new OrderSpecifier(Order.DESC, person.region));
    //        }
    //        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    //    }

    public List<Board> boards(Long id, OrderCondition orderCondition) {
        OrderSpecifier[] orderSpecifiers = createOrderSpecifier(orderCondition);

        return queryFactory.selectFrom(board)
                .where(board.id.gt(id))
                .orderBy(orderSpecifiers)
                .fetch();
    }
    public OrderSpecifier[] createOrderSpecifier(OrderCondition orderCondition) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

                if(Objects.isNull(orderCondition)){
                    orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.regDate));
                } else if (orderCondition.equals(OrderCondition.VIEW)) {
                    orderSpecifiers.add(new OrderSpecifier(Order.DESC, board.views));
                }
                return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }
}
