package com.mcp.client.controller;

import com.mcp.client.controller.dto.ChatRequest;
import com.mcp.client.service.ModelOrchestratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ModelOrchestratorService modelOrchestratorService;

    public ChatController(final ModelOrchestratorService modelOrchestratorService) {
        this.modelOrchestratorService = modelOrchestratorService;
    }

    @PostMapping(value = "/ai/ask", produces = "text/markdown; charset=UTF-8")
    public String ask(@RequestBody final ChatRequest req) {
        return this.modelOrchestratorService.invokeMistral(req);
    }
}
