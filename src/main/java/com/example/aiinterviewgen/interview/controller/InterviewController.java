package com.example.aiinterviewgen.interview.controller;

import com.example.aiinterviewgen.interview.domain.InterviewCreateDto;
import com.example.aiinterviewgen.interview.domain.InterviewResponseDto;
import com.example.aiinterviewgen.interview.domain.InterviewUpdateDto;
import com.example.aiinterviewgen.interview.exception.InterviewException;
import com.example.aiinterviewgen.interview.service.InterviewService;
import com.example.aiinterviewgen.member.security.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/interviews")
public class InterviewController {

    private final InterviewService interviewService;
    private final JwtProvider jwtProvider;

    private Authentication parseMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        return jwtProvider.getAuthentication(accessToken);
    }

    @PostMapping("")
    public ResponseEntity<Long> generateInterview(HttpServletRequest request, @RequestBody InterviewCreateDto createDto) {
        Authentication authentication = parseMember(request);
        String memberName = authentication.getName();
        return ResponseEntity.ok(interviewService.create(memberName, createDto.getQuestion()));
    }

    @DeleteMapping("/{interviewId}")
    public void deleteInterview(HttpServletRequest request, @PathVariable Long interviewId) {
        // member 검증 로직
        Authentication authentication = parseMember(request);
        InterviewResponseDto interview = interviewService.searchById(interviewId);

        if (!Objects.equals(authentication.getName(), interview.getMemberName())) {
            throw new InterviewException(404, "해당 게시물에 접근 권한이 없습니다.");
        }

        interviewService.delete(interviewId);
    }

    @PatchMapping("/{interviewId}")
    public void updateInterview(HttpServletRequest request, @PathVariable Long interviewId, @RequestBody InterviewUpdateDto updateDto) {
        // member 검증 로직
        Authentication authentication = parseMember(request);
        InterviewResponseDto interview = interviewService.searchById(interviewId);

        if (!Objects.equals(authentication.getName(), interview.getMemberName())) {
            throw new InterviewException(404, "해당 게시물에 접근 권한이 없습니다.");
        }

        interviewService.update(interviewId, updateDto);
    }

    @GetMapping("")
    public ResponseEntity<List<InterviewResponseDto>> searchAllInterviews(HttpServletRequest request) {
        Authentication authentication = parseMember(request);
        if (!authentication.isAuthenticated()) {
            throw new InterviewException(404, "해당 게시물에 접근 권한이 없습니다.");
        }
        return ResponseEntity.ok(interviewService.searchAll());
    }
}
