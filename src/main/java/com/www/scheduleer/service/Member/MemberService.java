package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;

//    public Member save(MemberDto memberDto) {
//
//        return memberRepository.save(
//                Member.builder()
//                        .name(memberDto.getName())
//                        .email(memberDto.getEmail())
//                        .password(passwordEncoder.encode(memberDto.getPassword()))
//                        .auth(Auth.USER)
//                        .type(Type.GENERAL)
//                        .picture(null)
//                        .build());
//    }

    public List<Member> findMembers(String email) {
        List<Member> members = memberRepository.findByEmailContaining(email);
        List<Member> m = new ArrayList<>();
        for (Member member : members) {
            m.add(member);
        }

        return m;
    }

    public Optional<Member> getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findMemberInfoById(memberId);
    }

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }
}
