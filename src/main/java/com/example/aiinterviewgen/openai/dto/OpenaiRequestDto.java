// OpenaiRequestDto.java

package com.example.aiinterviewgen.openai.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OpenaiRequestDto {

    private String model;
    private List<Message> messages;

    public OpenaiRequestDto(String model, String question) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message(question));
    }
}
