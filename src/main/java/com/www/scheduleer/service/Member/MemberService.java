package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.MemberInfo;
import com.www.scheduleer.web.dto.member.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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

        Optional<MemberInfo> memberInfo = this.memberRepository.findByEmail(email);
        if (memberInfo.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        MemberInfo memberInfoDto = memberInfo.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new MemberInfo(memberInfoDto.getEmail(), memberInfoDto.getPassword(), authorities);
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

    public void updatePassword(MemberInfo memberInfo, String newPassword) {
        System.out.println("새로운 비밀번호 : " + newPassword);
        memberInfo.setPassword(bc().encode(newPassword));
        memberRepository.save(memberInfo);
    }
}
