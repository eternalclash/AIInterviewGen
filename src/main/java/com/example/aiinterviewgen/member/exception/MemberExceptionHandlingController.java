package com.example.aiinterviewgen.member.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(500, "Internal Server Error");
        return ResponseEntity.status(500).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(401, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(MemberException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
        return ResponseEntity.status(ex.getErrorCode()).body(errorResponse);
    }
}
