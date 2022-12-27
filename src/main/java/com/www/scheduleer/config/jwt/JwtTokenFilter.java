package com.www.scheduleer.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.ErrorObject;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.config.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, RuntimeException {
        try {
            String jwt = resolveToken(request, AUTHORIZATION_HEADER);

            if (jwt != null) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
            } else {
                String refresh = resolveToken(request, REFRESH_HEADER);
                // refresh token을 확인해서 재발급해준다
                if (refresh != null) {
                    String newRefresh = jwtTokenProvider.reissueRefreshToken(refresh);
                    if (newRefresh != null) {
                        response.setHeader(REFRESH_HEADER, "Bearer-" + newRefresh);

                        // access token 생성
                        Authentication authentication = jwtTokenProvider.getAuthentication(refresh);
                        response.setHeader(AUTHORIZATION_HEADER, "Bearer-" + jwtTokenProvider.createAccessToken(authentication));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("reissue refresh Token & access Token");
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            setErrorResponse(response, ErrorCode.UNAUTHORIZED);
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getStatus(), errorCode.getCode(), errorCode.getMessage());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private String resolveToken(HttpServletRequest request, String header) {
        String bearerToken = request.getHeader(header);
        if (bearerToken != null && bearerToken.startsWith("Bearer-")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}