package com.www.scheduleer.Repository;

import com.www.scheduleer.VO.Member;
import com.www.scheduleer.VO.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<UserInfo, Long> {

//    void insertMember(Member member);

    Optional<UserInfo> findByEmail(String email);

}
