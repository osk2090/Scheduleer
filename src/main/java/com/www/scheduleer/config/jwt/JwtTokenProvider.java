package com.www.scheduleer.config.jwt;

import com.www.scheduleer.Repository.RefreshTokenRepository;
import com.www.scheduleer.config.error.CustomException;
import com.www.scheduleer.config.error.ErrorCode;
import com.www.scheduleer.service.member.AuthService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret-key}")
    String secretKey;
    @Value("${jwt.token-validity-in-sec}")
    long accessTokenExp;
    @Value("${jwt.refresh-token-validity-in-sec}")
    long refreshTokenExp;
    @Value("${jwt.issuer}")
    String issuer;
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {  // init()
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public String createAccessToken(String userInfo) {
        LocalDateTime iat = LocalDateTime.now();
        LocalDateTime exp = iat.plusSeconds(accessTokenExp);

        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userInfo)  // JWT 토큰 제목
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(iat))    // JWT 토큰 발급 시간
                .setExpiration(Timestamp.valueOf(exp))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }
    public Claims validateTokenAndGetSubject(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | IllegalArgumentException | MalformedJwtException |
                 UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.TOKEN_ERROR);
        }
    }

}
