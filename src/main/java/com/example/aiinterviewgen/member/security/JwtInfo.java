package com.example.aiinterviewgen.member.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}