package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.Repository.RefreshTokenRepository;
import com.www.scheduleer.config.jwt.JwtTokenProvider;
import com.www.scheduleer.controller.dto.member.MemberLoginResponseDto;
import com.www.scheduleer.controller.dto.member.PrincipalDetails;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

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

    /*sessionKey check / 로그인 시 loginCookie 값과 m_session_key의 값을 대조 일치하는 회원의 정보를 가져온다*/
    public Member checkMemberWithSessionKey(String sessionKey) {
        String email = refreshTokenRepository.findByToken(sessionKey).getUserId();
        return memberRepository.findByEmail(email).get();
    }


    @Transactional
    public Long signUp(String userId, String pw) { // 회원가입
        // 중복체크
        validateDuplicateUser(userId);
        String encodePw = passwordEncoder.encode(pw);

        return memberRepository.save(Member.testCreate(userId, encodePw)).getId();
    }

    @Transactional()
    public MemberLoginResponseDto signIn(Member member) {
        String refreshToken = jwtTokenProvider.createRefreshToken();
//        memberRepository.findByEmail(member.getEmail()).ifPresent(
//                updateMember -> updateMember.setRefreshToken(refreshToken)
//        );
        return new MemberLoginResponseDto(member.getId(), jwtTokenProvider.generateJwtToken(member), refreshToken);
    }

    private void validateDuplicateUser(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    log.debug("email : {}, 이메일 중복으로 회원가입 실패", email);
                    throw new RuntimeException("이메일 중복");
                });
    }

}
