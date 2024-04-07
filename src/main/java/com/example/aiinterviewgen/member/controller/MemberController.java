package com.example.aiinterviewgen.member.controller;

import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> create(@Valid @RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(memberService.join(memberDto));
    }
}
