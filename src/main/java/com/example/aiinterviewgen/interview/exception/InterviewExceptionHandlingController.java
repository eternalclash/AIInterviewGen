package com.example.aiinterviewgen.interview.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class InterviewExceptionHandlingController {
    @ExceptionHandler(InterviewException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(InterviewException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return ResponseEntity.status(ex.getErrorCode()).body(errorResponse);
    }
}
