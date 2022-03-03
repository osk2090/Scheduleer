package com.www.scheduleer;

import com.www.scheduleer.VO.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@SpringBootTest
@Log4j2
class ScheduleerApplicationTests {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();

    @Test
    void contextLoads() {
    }

    @Test
    void insertMember() {
        tx.begin();

        Member member = new Member("osk", "osk2090@naver.com", "1234");

        em.persist(member);

        tx.commit();
    }
}
