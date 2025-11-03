package com.mcp.client.service;

import org.springframework.stereotype.Service;

/**
 * Service that applies basic validation and normalization rules
 * to incoming user prompts.
 */
@Service
public class RequestValidatorService {

    public String analyzePrompt(final String prompt) {
        if (prompt == null || prompt.trim().isEmpty()) {
            return "🤔 I don't understand your request.";
        }
        return prompt.trim();
    }
}
