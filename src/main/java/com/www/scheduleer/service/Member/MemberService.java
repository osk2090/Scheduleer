package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.domain.Type;
import com.www.scheduleer.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    BCryptPasswordEncoder passwordEncoder;

    public Member save(MemberDto memberDto) {
        return memberRepository.save(
                Member.builder()
                        .name(memberDto.getName())
                        .email(memberDto.getEmail())
                        .password(passwordEncoder.encode(memberDto.getPassword()))
                        .auth(Auth.USER)
                        .type(Type.GENERAL)
                        .picture(null)
                        .build());
    }
}
