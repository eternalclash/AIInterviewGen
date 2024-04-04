package com.example.aiinterviewgen.service;

import com.example.aiinterviewgen.domain.Member;
import com.example.aiinterviewgen.domain.MemberDto;
import com.example.aiinterviewgen.domain.MemberException;
import com.example.aiinterviewgen.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(MemberDto memberDto) {
        validateDuplicateMember(memberDto.getName());
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setPassword(memberDto.getPassword());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(String memberName) {
        Optional<Member> findMember = memberRepository.findAllByName(memberName);
        if (findMember.isPresent()) {
            throw new MemberException("이미 존재하는 ID입니다.");
        }
    }
}
