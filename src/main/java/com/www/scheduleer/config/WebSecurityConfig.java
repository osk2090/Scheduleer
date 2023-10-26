package com.www.scheduleer.config;

import com.www.scheduleer.config.jwt.JwtAuthenticationEntryPoint;
import com.www.scheduleer.config.jwt.JwtTokenFilter;
import com.www.scheduleer.config.jwt.JwtTokenProvider;
import com.www.scheduleer.service.member.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 이게 뭐임?
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthService authService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
//                .antMatchers(HttpMethod.OPTIONS, "/**") // 이게 뭐임?
                .antMatchers(
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/h2-console/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        //session 사용 안함
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.authorizeRequests().antMatchers(
                "/api/member/signIn",
                "/api/member/signUp",
                "/api/board/list",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/webjars/**").permitAll().antMatchers("/api/**").authenticated();

        http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider,authService), BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
