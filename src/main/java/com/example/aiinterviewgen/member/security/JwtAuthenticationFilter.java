package com.example.aiinterviewgen.member.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken((HttpServletRequest) request);

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        else {
//            // accessToken 만료 시 refreshToken 검증 및 새로운 accessToken 발급 로직
//            String refreshToken = ((HttpServletRequest) request).getHeader("Refresh-Token");
//            if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
//                String newAccessToken = jwtTokenProvider.createAccessToken(/* refreshToken 또는 사용자 정보로부터 subject 추출 */);
//
//                // 새로운 accessToken으로 Authentication 객체 생성 및 SecurityContext에 저장
//                Authentication newAuthentication = jwtTokenProvider.getAuthentication(newAccessToken);
//                SecurityContextHolder.getContext().setAuthentication(newAuthentication);
//
//                // 응답 헤더에 새로운 accessToken 추가
//                ((HttpServletResponse) response).setHeader("Authorization", "Bearer " + newAccessToken);
//            }
//        }
        chain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "accessToken".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        return null;
    }
}