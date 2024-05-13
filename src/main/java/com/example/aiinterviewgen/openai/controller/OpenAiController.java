package com.example.aiinterviewgen.openai.controller;

import com.example.aiinterviewgen.openai.service.OpenaiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/openai")
public class OpenAiController {

    private final OpenaiService openaiService;

    public OpenAiController(OpenaiService openaiService) {
        this.openaiService = openaiService;
    }

    @GetMapping("/completion")
    public String completeAnswer(@RequestParam(name = "question")String question){
        return openaiService.completeAnswer(question);
    }

}
