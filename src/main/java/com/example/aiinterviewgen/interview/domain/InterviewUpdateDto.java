package com.example.aiinterviewgen.interview.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InterviewUpdateDto {
    private String question;
    private String answer;
}
