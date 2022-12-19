package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.controller.dto.member.PrincipalDetails;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            return new PrincipalDetails(member.get());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

}
