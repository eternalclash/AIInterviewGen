package com.example.aiinterviewgen.member.service;

import com.example.aiinterviewgen.member.domain.Member;
import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.exception.MemberException;
import com.example.aiinterviewgen.member.repository.MemberRepository;
import com.example.aiinterviewgen.member.security.JwtInfo;
import com.example.aiinterviewgen.member.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtInfo login(String memberName, String password) {
        try {
            // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
            // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberName, password);

            // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
            // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            return jwtProvider.generateToken(authentication);
        } catch (Exception e) {
            throw new MemberException(401, "ID 또는 비밀번호가 일치하지 않습니다. ");
        }
    }

    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getName());
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setPassword(memberDto.getPassword());
        member.setRoles(List.of("USER"));
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(String memberName) {
        Optional<Member> findMember = memberRepository.findAllByName(memberName);
        if (findMember.isPresent()) {
            throw new MemberException(401, "이미 존재하는 ID입니다.");
        }
    }
}
