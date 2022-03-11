package com.www.scheduleer.Repository;

import com.www.scheduleer.VO.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
