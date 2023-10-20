package com.www.scheduleer.service.member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.Repository.RefreshTokenRepository;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.config.jwt.JwtTokenProvider;
import com.www.scheduleer.config.utils.FileUtil;
import com.www.scheduleer.controller.dto.member.ChangePasswdDto;
import com.www.scheduleer.controller.dto.member.MemberInfoDto;
import com.www.scheduleer.controller.dto.member.MemberLoginResponseDto;
import com.www.scheduleer.controller.dto.member.SignUpDto;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.board.BoardService;
import com.www.scheduleer.service.sse.SseEmitters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final BoardService boardService;
    private final S3Uploader s3Uploader;
    private final FileUtil fileUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final SseEmitters sseEmitters; // sse 연결 관련

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @Transactional
    public Long signUp(SignUpDto signUpDto) throws Exception { // 회원가입
        // 중복체크
        validateDuplicateUser(signUpDto.getEmail());
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        String s3ObjectUrl = null;
        if (signUpDto.getPicture() != null) {
            s3ObjectUrl = s3Uploader.upload(signUpDto.getPicture(), "image", signUpDto.getEmail());
        }
        return memberRepository.save(Member.createEntity(signUpDto, s3ObjectUrl)).getId();
    }
    private void validateDuplicateUser(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    log.debug("email : {}, 이메일 중복으로 회원가입 실패", email);
                    throw new RuntimeException("이메일 중복");
                });
    }

    @Transactional()
    public MemberLoginResponseDto signIn(String email, String pw) {
        UserDetails userDetails = authService.loadUserByUsername(email);

        if(!passwordEncoder.matches(pw, userDetails.getPassword())){
//            throw new BadCredentialsException(userDetails.getUsername() + "Invalid password");
            throw new CustomException(ErrorCode.BAD_CREDENTIALS);
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

        log.info("signIn service | authentication.getName : {}, authentication.getCredentials() : {}",
                authentication.getName(), authentication.getCredentials());

        // SSE id값 저장
//        String sseId = sseEmitters.connect();
//        sseService.saveSseId(sseId, authService.getMember());

        return new MemberLoginResponseDto(
                "Bearer-" + jwtTokenProvider.createAccessToken(authentication),
                "Bearer-" + jwtTokenProvider.issueRefreshToken(authentication));
    }

    public void changePw(ChangePasswdDto changePasswd, Member member) {
        if (changePasswd.getBeforePasswd().equals(changePasswd.getAfterPasswd())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        if (!passwordEncoder.matches(changePasswd.getBeforePasswd(), member.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        member.setPassword(passwordEncoder.encode(changePasswd.getAfterPasswd()));
        memberRepository.save(member);
    }

    public MemberInfoDto getMemberInfo(Member member) {
        Optional<Member> m = this.getMember(member.getEmail());
        Member getMember = null;

        if (m.isPresent()) {
            getMember = m.get();
        }

        return MemberInfoDto.builder()
                .name(getMember.getName())
                .email(getMember.getEmail())
                .password(getMember.getPassword())
                .picture(getMember.getPicture())
                .build();
    }
}
