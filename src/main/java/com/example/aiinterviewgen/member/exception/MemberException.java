package com.example.aiinterviewgen.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public MemberException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
