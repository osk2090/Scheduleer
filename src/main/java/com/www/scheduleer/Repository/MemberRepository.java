package com.www.scheduleer.Repository;

import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberInfo, Long> {

    List<MemberInfoDto> findByEmailContaining(String email);

    Optional<MemberInfoDto> findByEmail(String email);//이메일을 통한 회원 조회 위함

    Optional<MemberInfoDto> findMemberInfoById(Long id);
}
