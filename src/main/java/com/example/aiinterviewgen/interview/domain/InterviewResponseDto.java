package com.example.aiinterviewgen.interview.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InterviewResponseDto {
    private String question;
    private String answer;
    private String keyword;
    private String memberName;

    public static InterviewResponseDto toDto(Interview interview) {
        InterviewResponseDto dto = new InterviewResponseDto();
        dto.setQuestion(interview.getQuestion());
        dto.setAnswer(interview.getAnswer());
        dto.setKeyword(interview.getKeyword());
        dto.setMemberName(interview.getMember().getUsername());
        return dto;
    }
}
