package com.example.aiinterviewgen.member.controller;

import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.security.JwtInfo;
import com.example.aiinterviewgen.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.join(memberDto));
    }

    @PostMapping("/signin/password")
    public ResponseEntity<JwtInfo> singIn(@Valid @RequestBody MemberDto memberDto) {
        String memberName = memberDto.getName();
        String password = memberDto.getPassword();
        JwtInfo jwtInfo = memberService.login(memberName, password);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtInfo.getAccessToken())
                .body(jwtInfo);
    }
}
