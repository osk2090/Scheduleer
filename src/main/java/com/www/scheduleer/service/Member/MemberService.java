package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.VO.MemberInfo;
import com.www.scheduleer.VO.security.MemberInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Long save(MemberInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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

}
