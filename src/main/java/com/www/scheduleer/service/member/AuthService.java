package com.www.scheduleer.service.member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.controller.dto.member.PrincipalDetails;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private Member m;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email : " + email + " was not found"));
        m = member;
        return new PrincipalDetails(member);
    }

    public Member getMember() {
        return m;
    }
}
