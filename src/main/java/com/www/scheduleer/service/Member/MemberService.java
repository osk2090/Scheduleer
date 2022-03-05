package com.www.scheduleer.service.Member;

import com.www.scheduleer.VO.Member;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Service
public class MemberService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();

    public void addMember(Member member) {
        tx.begin();

        Member m = new Member();
        m.setName(member.getName());
        m.setEmail(member.getEmail());
        m.setPassword(member.getPassword());

        em.persist(m);
        tx.commit();
    }
}
