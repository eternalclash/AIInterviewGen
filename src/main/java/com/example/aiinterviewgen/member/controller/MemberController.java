package com.example.aiinterviewgen.member.controller;

import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.security.JwtInfo;
import com.example.aiinterviewgen.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.join(memberDto));
    }

    @PostMapping("/signin/password")
    public ResponseEntity<JwtInfo> singIn(@Valid @RequestBody MemberDto memberDto, HttpServletResponse response) {
        String memberName = memberDto.getName();
        String password = memberDto.getPassword();
        JwtInfo jwtInfo = memberService.login(memberName, password);
        Cookie accessToken = new Cookie("accessToken", jwtInfo.getAccessToken());
        accessToken.setHttpOnly(true); // 클라이언트 측 스크립트로부터 쿠키를 보호
        accessToken.setSecure(true); // HTTPS 통신에서만 쿠키를 전송하도록 설정
        accessToken.setPath("/"); // 모든 경로에서 쿠키 사용
        response.addCookie(accessToken); // 쿠키를 응답에 추가

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtInfo.getAccessToken())
                .body(jwtInfo);
    }
}
