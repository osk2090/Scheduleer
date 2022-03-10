package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");
//
//    EntityManager em = emf.createEntityManager();
//
//    EntityTransaction tx = em.getTransaction();

    @Transactional
    public void addMember(Member member) {
        Member m = new Member();
        m.setName(member.getName());
        m.setEmail(member.getEmail());
        m.setPassword(member.getPassword());
    }

//    public Member getMember(String email) {
//        Member findMember = em.find(Member.class, email);
//        System.out.println("findMember = " + findMember.toString());
//        return findMember;
//    }

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }
}
