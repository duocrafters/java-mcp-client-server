package com.mcp.frontend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class AiClientService {

    private final RestClient restClient;

    public AiClientService(@Value("${mcp.client.base-url:http://localhost:8080}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String ask(String userPrompt) {
        return restClient.post()
                .uri("/ai/ask")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.valueOf("text/markdown"))
                .body(Map.of("userPrompt", userPrompt))
                .retrieve()
                .body(String.class);
    }
}
