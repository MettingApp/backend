package server.meeting.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private final long ACCESS_TOKEN_VALIDATION_TIME = 30 * 60 * 1000L; // 30분
    private final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60L * 60L * 24L * 14; // 2주
    private final static String BEARER_PREFIX = "Bearer ";
    private final static String ROLE_PREFIX = "role";


    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public boolean validateToken(String token) {
        if (token == null) {
            return false;
        }

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    public TokenDto generateToken(String username, List<String> roles) {
        List<SimpleGrantedAuthority> authorities = convertRolesToAuthorities(roles);

        String role = getGeneralRoleFrom(authorities);

        String refreshToken = createRefreshToken(username, role);
        String accessToken = createAccessToken(username, role);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private List<SimpleGrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    private String getGeneralRoleFrom(List<SimpleGrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String createRefreshToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ROLE_PREFIX, role);

        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + REFRESH_TOKEN_VALIDATION_TIME)) // 토큰의 만료일시를 설정한다.
                .signWith(key, SignatureAlgorithm.HS256) // 지정된 서명 알고리즘과 비밀 키를 사용하여 토큰을 서명한다.
                .compact();
    }

    public String createAccessToken(String username, String role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(ROLE_PREFIX,role);

        return BEARER_PREFIX + Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(new Date().getTime() + ACCESS_TOKEN_VALIDATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (!claims.containsKey(ROLE_PREFIX)) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = getRoleListFrom(claims);

        // password가 없는데 이렇게 작성하면 보안 문제가 발생할 거 같음. 나중에 확인해 보자.
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private List<SimpleGrantedAuthority> getRoleListFrom(Claims claims) {
        return Arrays.stream(claims.get(ROLE_PREFIX).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get(ROLE_PREFIX, String.class);
    }

}

