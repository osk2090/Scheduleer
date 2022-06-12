package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private BCryptPasswordEncoder encoder;

    public Long save(MemberInfoDto infoDto) {
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return memberRepository.save(MemberInfo.builder()
                .name(infoDto.getName())
                .email(infoDto.getEmail())
                .auth(infoDto.getAuth())
                .picture(infoDto.getPicture())
                .password(infoDto.getPassword()).build()).getId();
    }

    public List<MemberInfo> getMemberList() {
        return memberRepository.findAll();
    }

    public Optional<MemberInfo> getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<MemberInfo> findMembers(String email) {
        List<MemberInfo> members = memberRepository.findByEmailContaining(email);
        List<MemberInfo> m = new ArrayList<>();
        for (MemberInfo member : members) {
            m.add(member);
        }

        return m;
    }

    @Override
    public MemberInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).orElseThrow (() -> new UsernameNotFoundException(email));
    }

    private void validateDuplicateMember(MemberInfo memberInfo) {
        Optional<MemberInfo> findMember = memberRepository.findByEmail(memberInfo.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public List<MemberInfo> findAllDesc() {
        return memberRepository.findAll();
    }

    public Optional<MemberInfo> findMemberInfoFromMemberInfoDTO(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<MemberInfo> findMember(Long memberId) {
        return memberRepository.findMemberInfoById(memberId);
    }

    public BCryptPasswordEncoder bc() {
        return encoder = new BCryptPasswordEncoder();
    }

    public void modifyPw(MemberInfoDto memberInfoDto) {
        System.out.println("암호화 전 비밀번호 : " + memberInfoDto.getPassword());
        memberInfoDto.setPassword(bc().encode(memberInfoDto.getPassword()));
        System.out.println("암호화 후 비밀번호 : " + memberInfoDto.getPassword());
        save(memberInfoDto);
    }
}
