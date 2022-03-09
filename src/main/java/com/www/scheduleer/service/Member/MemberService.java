package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.Member;
import com.www.scheduleer.VO.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

//        Member m = new Member();
//        m.setName(member.getName());
//        m.setEmail(member.getEmail());
//        m.setPassword(member.getPassword());
//
//        em.persist(m);
        tx.commit();
    }

    public Member getMember(String email) {
        Member findMember = em.find(Member.class, email);
        System.out.println("findMember = " + findMember.toString());
        return findMember;
    }
}
