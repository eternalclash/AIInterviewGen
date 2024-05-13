// OpenaiServiceImpl.java

package com.example.aiinterviewgen.openai.service;

import com.example.aiinterviewgen.openai.dto.OpenaiRequestDto;
import com.example.aiinterviewgen.openai.dto.OpenaiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenaiServiceImpl implements OpenaiService {
    private final RestTemplate template;

    public OpenaiServiceImpl(RestTemplate template) {
        this.template = template;
    }

    @Value("${openai.api.url}")
    private String apiURL;

    @Value("${openai.model}")
    private String model;

    @Override
    public String completeAnswer(String question) {
        OpenaiRequestDto requestDto = new OpenaiRequestDto(model, question); // 모델 정보를 전달합니다.
        OpenaiResponseDto chatGPTResponse =  template.postForObject(apiURL, requestDto, OpenaiResponseDto.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }
}
