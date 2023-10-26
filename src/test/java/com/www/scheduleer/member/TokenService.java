package com.www.scheduleer.member;

import com.www.scheduleer.controller.dto.member.UploadReqDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
public class TokenService {

    @Value("${jwt.secret-key}")
    String secretKey;
    @Value("${jwt.token-validity-in-sec}")
    long accessTokenExp;
    @Value("${jwt.refresh-token-validity-in-sec}")
    long refreshTokenExp;
    @Value("${jwt.issuer}")
    String issuer;

    @Test
    @DisplayName("JWT 발급 테스트")
    public void generateToken() throws IOException {
        LocalDateTime iat = LocalDateTime.now();
        LocalDateTime exp = iat.plusSeconds(accessTokenExp);

        String jwt = Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject("osk")  // JWT 발급 유저
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(iat))    // JWT 토큰 발급 시간
                .setExpiration(Timestamp.valueOf(exp))    // JWT 토큰 만료 시간
                .compact();// JWT 토큰 생성
        System.out.println("jwt result: " + jwt);

    }
}
