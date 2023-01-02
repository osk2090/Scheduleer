package com.www.scheduleer.service.Member;

import com.google.cloud.storage.BlobInfo;
import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.Repository.RefreshTokenRepository;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.config.jwt.JwtTokenProvider;
import com.www.scheduleer.config.utils.FileUtil;
import com.www.scheduleer.controller.dto.member.*;
import com.www.scheduleer.domain.Board;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.domain.UploadReqDto;
import com.www.scheduleer.service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private final GCSService gcsService;
    private final FileUtil fileUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

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
    public Long signUp(SignUpDto signUpDto) throws IOException { // 회원가입
        // 중복체크
        validateDuplicateUser(signUpDto.getEmail());
        signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        UploadReqDto uploadReqDto = new UploadReqDto();

        BlobInfo blobInfo = null;
        if (signUpDto.getPicture() != null) {

            String saveFilePath = fileUtil.save(signUpDto.getPicture());

            uploadReqDto.setBucketName("scheduleer");
            uploadReqDto.setUploadFileName("profile/" + signUpDto.getEmail() + ".jpg");
            uploadReqDto.setLocalFileLocation(saveFilePath);
            blobInfo = gcsService.uploadFileToGCS(uploadReqDto);
            fileUtil.delete(signUpDto.getPicture());
        }

        return memberRepository.save(Member.createEntity(signUpDto, blobInfo == null ? null : blobInfo.getMediaLink())).getId();
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

    public MemberInfo getMemberInfo(String email) {
        Optional<Member> m = this.getMember(email);
        List<Board> b = boardService.findBoardInfoByWriterEmail(email);

        Member getMember = null;
        List<BoardInfo> boardInfoList = new ArrayList<>();

        if (b.size() > 0) {
            b.forEach(data -> {
                boardInfoList.add(BoardInfo.builder()
                        .title(data.getTitle())
                        .createDate(data.getRegDate())
                        .isCheck(data.getCheckStar())
                        .build());
            });
        }

        if (m.isPresent()) {
            getMember = m.get();
        }
        return MemberInfo.builder().name(getMember.getName()).email(getMember.getEmail()).password(getMember.getPassword()).picture(getMember.getPicture()).boardInfoList(boardInfoList).build();
    }
}
