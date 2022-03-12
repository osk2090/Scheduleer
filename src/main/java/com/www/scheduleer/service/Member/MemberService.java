package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    public List<Member> findMember(String email) {
        List<Member> members = memberRepository.findByEmailContaining(email);
        List<Member> m = new ArrayList<>();
        for (Member member : members) {
            m.add(member);
        }

        return m;
    }
}
