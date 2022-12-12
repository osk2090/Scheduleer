package com.www.scheduleer.service.Member;

import com.www.scheduleer.Repository.MemberRepository;
import com.www.scheduleer.web.domain.Auth;
import com.www.scheduleer.web.domain.Member;
import com.www.scheduleer.web.domain.Type;
import com.www.scheduleer.web.dto.member.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> memberInfo = this.memberRepository.findByEmail(email);
        Member memberDto = memberInfo.orElse(null);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(memberDto.getAuth().toString()));

        return (UserDetails) Member.builder().email(memberDto.getEmail()).password(memberDto.getPassword()).build();
    }

    public Member save(MemberDto infoDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        infoDto.setPassword(passwordEncoder.encode(infoDto.getPassword()));
        return memberRepository.save(infoDto.toEntity());
    }

    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMember(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> findMembers(String email) {
        List<Member> members = memberRepository.findByEmailContaining(email);
        List<Member> m = new ArrayList<>();
        for (Member member : members) {
            m.add(member);
        }

        return m;
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    public List<Member> findAllDesc() {
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberInfoFromMemberInfoDTO(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findMember(Long memberId) {
        return memberRepository.findMemberInfoById(memberId);
    }

//    public BCryptPasswordEncoder bc() {
//        return encoder = new BCryptPasswordEncoder();
//    }
//
//    public void updatePassword(Member member, String newPassword) {
//        System.out.println("새로운 비밀번호 : " + newPassword);
//        member.setPassword(bc().encode(newPassword));
//        memberRepository.save(member);
//    }
}
