package com.www.scheduleer.config.token;

import com.www.scheduleer.service.Member.MemberService;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenService tokenService;
    private final MemberService memberService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("Auth");
        if (token != null && tokenService.verifyToken(token)) {
            String email = tokenService.getUid(token);
            Optional<Member> memberDto = memberService.findMemberInfoFromMemberInfoDTO(email);
            if (memberDto.isPresent()) {
                Authentication auth = getAuthentication(memberDto.get());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
    }

    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
