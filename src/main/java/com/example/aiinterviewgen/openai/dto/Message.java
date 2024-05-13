package com.example.aiinterviewgen.openai.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Message {
    @Value("${openai.role}")
    private String role;
    private String content;

    public Message(String content) {
        this.content = content;
    }
}