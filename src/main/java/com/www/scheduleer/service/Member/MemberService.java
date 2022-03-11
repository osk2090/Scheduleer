package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        memberRepository.save(member);
    }

//    public Member getMember(String email) {
//        Member findMember = em.find(Member.class, email);
//        System.out.println("findMember = " + findMember.toString());
//        return findMember;
//    }

    public List<Member> getMemberList() {
        List<Member> memberList = new ArrayList<>();

        System.out.println(memberRepository.findAll().isEmpty());

        for (Member m : memberRepository.findAll()) {
            memberList.add(m);
        }
        return memberList;
    }
}
