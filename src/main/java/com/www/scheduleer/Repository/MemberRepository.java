package com.www.scheduleer.Repository;

import com.www.scheduleer.web.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByEmailContaining(String email);

    Optional<Member> findByEmail(String email);//이메일을 통한 회원 조회 위함

    Optional<Member> findMemberInfoById(Long id);
}
