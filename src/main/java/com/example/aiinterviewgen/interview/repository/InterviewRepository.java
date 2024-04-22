package com.example.aiinterviewgen.interview.repository;

import com.example.aiinterviewgen.interview.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
