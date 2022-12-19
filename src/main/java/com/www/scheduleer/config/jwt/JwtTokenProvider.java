package com.www.scheduleer.config.jwt;

import com.www.scheduleer.controller.dto.member.PrincipalDetails;
import com.www.scheduleer.domain.Member;
import com.www.scheduleer.service.Member.AuthService;
import com.www.scheduleer.service.Member.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final AuthService authService;

    private final Long expiredTime = 1000L * 60 * 60 * 24 * 365; // 임시 유효시간 1년
    private final long refreshTokenValidTime = 1000L * 60 * 60 * 24 * 7; // 7일

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /** * Member 정보를 담은 JWT 토큰을 생성한다.
     *
     * @param member Member 정보를 담은 객체
     * @return String JWT 토큰 */
    public String generateJwtToken(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(member.getEmail()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(member)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + expiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 사용하여 암호화
        header.put("regDate", System.currentTimeMillis()); return header;
    }

    /**
     * 클레임(Claim)을 생성한다.
     * @param member 토큰을 생성하기 위한 계정 정보를 담은 객체
     * @return Map<String, Object> 클레임(Claim)
     */
    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        claims.put("name", member.getName());
        return claims;
    }

    /**
     * Token 에서 Claim 을 가져온다.
     * @param token JWT 토큰
     * @return Claims 클레임
     */
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * 토큰으로 부터 username 을 가져온다.
     * @param token JWT 토큰
     * @return String Member 의 username
     */
    public String getUsernameFromToken(String token) {
        return (String) getClaims(token).get("username");
    }

    public String getMemberEmail(String token){
        return (String) getClaims(token).get("email");
    }

    public Authentication getAuthentication(String token) {
        PrincipalDetails userDetails = (PrincipalDetails) authService.loadUserByUsername(getMemberEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * 토큰으로 부터 인가 정보를 가져온다.
     * @param token JWT 토큰
     * @return String grade
     */
    public String getMemberRoleSetFromToken(String token) {
        return (String) getClaims(token).get("roles");
    }

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }


    public boolean validateTokenExceptExpiration(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

}