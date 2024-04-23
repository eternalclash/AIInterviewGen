package com.example.aiinterviewgen.interview.service;

import com.example.aiinterviewgen.interview.domain.Interview;
import com.example.aiinterviewgen.interview.domain.InterviewResponseDto;
import com.example.aiinterviewgen.interview.domain.InterviewUpdateDto;
import com.example.aiinterviewgen.interview.exception.InterviewException;
import com.example.aiinterviewgen.interview.repository.InterviewRepository;
import com.example.aiinterviewgen.member.domain.Member;
import com.example.aiinterviewgen.member.exception.MemberException;
import com.example.aiinterviewgen.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final MemberRepository memberRepository;

    private Member loadMember(String memberName) {
        return memberRepository.findAllByName(memberName)
                .orElseThrow(() -> new MemberException(401, "사용자를 찾을 수 없습니다."));
    }

    private Interview loadInterview(Long interviewId) {
        return interviewRepository.findById(interviewId)
                .orElseThrow(() -> new InterviewException(401, "존재하지 않는 게시물입니다."));
    }

    public Long create(String memberName, String question) {
        // 사용자 정보 조회
        Member member = loadMember(memberName);

        // Interview 객체 생성 및 저장
        Interview interview = new Interview();
        interview.createInterview(member, question);
        interviewRepository.save(interview);
        return interview.getId();
    }

    public void delete(Long interviewId) {
        Interview interview = loadInterview(interviewId);
        interviewRepository.delete(interview);
    }

    public void update(Long interviewId, InterviewUpdateDto updateDto) {
        Interview interview = loadInterview(interviewId);
        interview.updateQnA(updateDto);
        interviewRepository.save(interview);
    }

    public InterviewResponseDto searchById(Long interviewId) {
        Interview interview = loadInterview(interviewId);
        return InterviewResponseDto.toDto(interview);
    }

    public List<InterviewResponseDto> searchAll() {
        return interviewRepository.findAll().stream()
                .map(InterviewResponseDto::toDto)
                .collect(Collectors.toList());
    }
}
