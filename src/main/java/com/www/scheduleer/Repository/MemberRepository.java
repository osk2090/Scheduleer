package com.www.scheduleer.Repository;

import com.www.scheduleer.VO.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
