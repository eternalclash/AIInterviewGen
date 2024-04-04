package com.example.aiinterviewgen.controller;

import com.example.aiinterviewgen.domain.Member;
import com.example.aiinterviewgen.domain.MemberDto;
import com.example.aiinterviewgen.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public String create(@Valid @RequestBody MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setPassword(memberDto.getPassword());
        memberService.join(member);
        return "redirect:/";
    }
}
