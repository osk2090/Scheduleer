package com.www.scheduleer.service.Board;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.Repository.RefreshTokenRepository;
import com.www.scheduleer.config.MyUserDetailsService;
import com.www.scheduleer.config.jwt.JwtTokenProvider;
import com.www.scheduleer.controller.dto.member.SignInResponseDto;
import com.www.scheduleer.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MyUserDetailsService myUserDetailsService;
    private final RefreshTokenRepository repository; // 삭제

    @Transactional
    public Long signUp(String userId, String pw){ // 회원가입
        // 중복체크
        validateDuplicateUser(userId);
        String encodePw = passwordEncoder.encode(pw);

        return memberRepository.save(Member.testCreate(userId, encodePw)).getId();
    }

    @Transactional()
    public SignInResponseDto signIn(String userId, String pw) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userId);

        if(!passwordEncoder.matches(pw, userDetails.getPassword())){
            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
        }

        Authentication authentication =  new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        log.info("signIn service | authentication.getName : {}, authentication.getCredentials() : {}",
                authentication.getName(), authentication.getCredentials());

        return new SignInResponseDto(
                "Bearer-"+jwtTokenProvider.createAccessToken(authentication),
                "Bearer-"+jwtTokenProvider.issueRefreshToken(authentication));
    }

    private void validateDuplicateUser(String email){
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    log.debug("email : {}, 이메일 중복으로 회원가입 실패", email);
                    throw new RuntimeException("이메일 중복");
                });
    }
}