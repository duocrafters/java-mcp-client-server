package com.mcp.client.service;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.MistralAiChatOptions;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.ai.model.tool.DefaultToolExecutionEligibilityPredicate;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionEligibilityPredicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class MistralService {
    private final ToolExecutionEligibilityPredicate toolExecutionEligibilityPredicate;
    private final ToolCallingManager toolCallingManager;
    @Value("${spring.ai.mistralai.api-key}")
    private String mistralApiKey;

    public MistralService() {
        this.toolExecutionEligibilityPredicate = new DefaultToolExecutionEligibilityPredicate();
        this.toolCallingManager = ToolCallingManager.builder().build();
    }

    public MistralAiChatModel createMistralChatModel() {
        return new MistralAiChatModel(
                new MistralAiApi(mistralApiKey),
                MistralAiChatOptions.builder()
                        .model(MistralAiApi.ChatModel.SMALL.getValue())
                        .temperature(0.4)
                        .maxTokens(500)
                        .build(),
                toolCallingManager,
                RetryTemplate.defaultInstance(),
                ObservationRegistry.NOOP,
                toolExecutionEligibilityPredicate
        );
    }
}
