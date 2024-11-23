package server.meeting.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import server.meeting.global.config.SecurityConfig;
import server.meeting.global.exception.ErrorType;
import server.meeting.global.util.JwtProvider;

import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(Arrays.asList(SecurityConfig.ALLOWED_URL).contains(uri)){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = resolveToken(request);

            if (jwtProvider.validateToken(token)) {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);

        } catch (SecurityException | MalformedJwtException e) {
            setErrorResponse(response, "잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, "만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            setErrorResponse(response, "지원하지 않는 토큰입니다.");
        } catch (IllegalArgumentException e) {
            setErrorResponse(response, "JWT 토큰이 잘못되었습니다.");
        }


    }


    private void setErrorResponse(
            HttpServletResponse response,
            String message
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
