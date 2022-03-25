package com.www.scheduleer;

import com.www.scheduleer.Repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
class ScheduleerApplicationTests {

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");
//
//    EntityManager em = emf.createEntityManager();
//
//    EntityTransaction tx = em.getTransaction();

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void insertMember() {
//        Member member = new Member("osk", "오은석", "osk2090@naver.com", "1111", MemberRole.GENERAL);
//        System.out.println(member.toString());
//        memberRepository.save(member);
    }
}
