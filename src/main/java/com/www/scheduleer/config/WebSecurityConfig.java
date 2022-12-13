package com.www.scheduleer.config;

import com.www.scheduleer.config.token.JwtAuthFilter;
import com.www.scheduleer.config.token.TokenService;
import com.www.scheduleer.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity // 1
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Autowired
    TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    private final AuthenticationFailureHandler authenticationFailureHandler;//로그인 실패 핸들러 의존성 주입

    private final CustomOAuth2UserService customOAuth2UserService;
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/css/**", "/js/**", "/image/**",
                        "/favicon.ico", "resources/**", "/error");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable() // rest api이므로 기본설정 미사용
                .csrf().disable() // rest api이므로 csrf 보안 미사용
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt로 인증하므로 세션 미사용
//                .and()
//                .authorizeRequests() // 6
//                .antMatchers("/signup1", "/signup", "/index").permitAll()//회원가입 및 약관동의 비로그인 접근가능
//                .antMatchers("/login", "/member", "/main", "/board/detail/**").permitAll() // 누구나 접근 허용
//                .antMatchers("/").hasRole("USER") // USER, ADMIN만 접근 가능
//                .antMatchers("/admin/admin", "/member/list").hasRole("ADMIN") // ADMIN만 접근 가능
//                .anyRequest().authenticated(); // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
//        http
//                .loginPage("/member/login") // 로그인 페이지 링크
//                .defaultSuccessUrl("/board/main") // 로그인 성공 후 리다이렉트 주소
//                .failureHandler(authenticationFailureHandler)
//                .and()
//                .logout() // 8
//                .logoutSuccessUrl("/board/main") // 로그아웃 성공시 리다이렉트 주소
//                .invalidateHttpSession(true) // 세션 날리기
                .and()
                //구글로그인 구현
                .oauth2Login()
//                .loginPage("/member/login")//구현한 로그인페이지 이용
//                .defaultSuccessUrl("/board/main")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        http.addFilterBefore(new JwtAuthFilter(tokenService, memberService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }
}
