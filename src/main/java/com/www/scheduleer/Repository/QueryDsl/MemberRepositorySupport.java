package com.www.scheduleer.Repository.QueryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.www.scheduleer.domain.QMember.member;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;


    public MemberRepositorySupport(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    public List<Member> members(String nickname) {
        return queryFactory.selectFrom(member).where(member.nickName.eq(nickname)).fetch();
    }
}
