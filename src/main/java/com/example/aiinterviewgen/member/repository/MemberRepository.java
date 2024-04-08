package com.example.aiinterviewgen.member.repository;

import com.example.aiinterviewgen.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findAllByName(String memberName);
}
