package com.www.scheduleer.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.config.error.ErrorResponse;
import com.www.scheduleer.service.member.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, CustomException {
        try {
            String token = parseBearerToken(request);
            if (token != null) {
                Authentication authentication = parseUserSpecification(token);
                log.info("authentication: {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e); // exception:e 형식으로 요청값에 저장
            log.error("token error: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    private Authentication parseUserSpecification(String token) {
        Claims claims = jwtTokenProvider.validateTokenAndGetSubject(token);
        UserDetails userDetails = authService.loadUserByUsername(claims.getSubject().split(":")[0]);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
