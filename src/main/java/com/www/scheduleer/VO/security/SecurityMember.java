package com.www.scheduleer.VO.security;

import com.www.scheduleer.VO.Member;
import com.www.scheduleer.VO.MemberRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SecurityMember extends User {
    private static final String ROLE_PREFIX = "ROLE_";

    private static final Long serialVersionUID = 1L;

    public SecurityMember(Member member) {
        super(member.getUserId(), member.getPassword(), makeGrantedAuthority(member.getMemberRole()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(MemberRole roles) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + roles.toString()));
        System.out.println(roles);
        return list;
    }
}
