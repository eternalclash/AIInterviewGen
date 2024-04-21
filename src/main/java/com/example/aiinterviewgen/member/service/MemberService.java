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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtInfo login(String memberName, String password) {
        try {
            // 1. Login ID를 기반으로 Member 정보 조회
            Member member = memberRepository.findAllByName(memberName)
                    .orElseThrow(() -> new MemberException(401, "존재하지 않는 ID입니다."));

            // 2. 입력한 비밀번호와 저장된 암호화된 비밀번호를 비교
            if (!passwordEncoder.matches(password, member.getPassword())) {
                throw new MemberException(401, "ID 또는 비밀번호가 일치하지 않습니다.");
            }

            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(memberName, password);
            return jwtProvider.generateToken(authentication);
        } catch (MemberException e) {
            throw e;
        } catch (Exception e) {
            throw new MemberException(401, "로그인에 실패하였습니다.");
        }
    }

    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getName());
        Member member = new Member();
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        member.updateInfo(memberDto);
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
