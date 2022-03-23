package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.VO.security.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("scheduleer");
//
//    EntityManager em = emf.createEntityManager();
//
//    EntityTransaction tx = em.getTransaction();

    @Transactional
    public Long save(MemberInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return memberRepository.save(MemberInfo.builder()
                .name(infoDto.getName())
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword()).build()).getId();
    }

    public List<MemberInfo> getMemberList() {
        return memberRepository.findAll();
    }

    public List<MemberInfo> findMember(String email) {
        List<MemberInfo> members = memberRepository.findByEmailContaining(email);
        List<MemberInfo> m = new ArrayList<>();
        for (MemberInfo member : members) {
            m.add(member);
        }

        return m;
    }

    @Override
    public MemberInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
