package com.example.aiinterviewgen.member.service;

import com.example.aiinterviewgen.member.domain.Member;
import com.example.aiinterviewgen.member.domain.MemberDto;
import com.example.aiinterviewgen.member.exception.MemberException;
import com.example.aiinterviewgen.member.repository.MemberRepository;
import com.example.aiinterviewgen.member.security.JwtInfo;
import com.example.aiinterviewgen.member.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // 사용자의 권한 정보를 기반으로 GrantedAuthority 리스트 생성
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // 사용자 이름, 비밀번호, 권한 정보를 포함한 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password, authorities);
        return authenticationToken;
    }

    public JwtInfo login(String memberName, String password) {
        try {
            Authentication authentication = authenticate(memberName, password);
            return jwtProvider.generateToken(authentication);
        } catch (MemberException e) {
            throw e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new MemberException(401, "로그인에 실패하였습니다.");
        }
    }

    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getName());
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        Member member = new Member();
        member.updateInfo(memberDto);
        member.setDefault();
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
