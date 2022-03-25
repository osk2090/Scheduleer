package com.www.scheduleer.Repository;

import com.www.scheduleer.VO.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberInfo, Long> {

    List<MemberInfo> findByEmailContaining(String email);

    Optional<MemberInfo> findByEmail(String email);//이메일을 통한 회원 조회 위함
}
