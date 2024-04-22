package com.example.aiinterviewgen.interview.domain;

import com.example.aiinterviewgen.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id", updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    private String keyword;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;

    public void createInterview(Member member, String question) {
        this.member = member;
        this.question = question;
    }

    public void updateQnA(InterviewUpdateDto updateDto) {
        this.question = updateDto.getQuestion();
        this.answer = updateDto.getAnswer();
    }
}
