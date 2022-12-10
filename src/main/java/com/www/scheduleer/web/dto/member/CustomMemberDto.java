package com.www.scheduleer.web.dto.member;

import com.www.scheduleer.web.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.*;

@Getter
public class CustomMemberDto implements SocialMemberInterface {
    private Long id;
    private String customName;
    private String password;
    private DefaultOAuth2User defaultOAuth2User;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private final Set<GrantedAuthority> authorities;

    public CustomMemberDto(Member member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        new User(username, password, authorities);
        this.customName = member.getName();
        this.password = member.getPassword();
        this.id = member.getId();
        this.authorities = Collections.unmodifiableSet(new LinkedHashSet<>(this.sortAuthorities(authorities)));
    }

    private Set<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(Comparator.comparing(GrantedAuthority::getAuthority));
        sortedAuthorities.addAll(authorities);
        return sortedAuthorities;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public <A> A getAttribute(String name) {
        return SocialMemberInterface.super.getAttribute(name);
    }
}
