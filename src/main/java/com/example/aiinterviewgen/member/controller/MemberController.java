package com.example.aiinterviewgen.member.controller;

import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.security.JwtInfo;
import com.example.aiinterviewgen.member.service.MemberService;
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
    public ResponseEntity<JwtInfo> singIn(@Valid @RequestBody MemberDto memberDto) {
        String memberName = memberDto.getName();
        String password = memberDto.getPassword();
        return ResponseEntity.ok(memberService.login(memberName, password));
    }
}
