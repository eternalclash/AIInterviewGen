package com.example.aiinterviewgen.interview.exception;

import lombok.Getter;

@Getter
public class InterviewException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public InterviewException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
