package com.www.scheduleer.Repository;

import com.www.scheduleer.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByEmailContaining(String email);

    Optional<Member> findByEmail(String email);//이메일을 통한 회원 조회 위함

    Optional<Member> findMemberInfoById(Long id);

    Optional<Member> findByEmailAndPassword(String email, String password);


//    Member findBySessionKeyAndSessionLimitAfter(String sessionKey, LocalDateTime sessionLimit);
}
