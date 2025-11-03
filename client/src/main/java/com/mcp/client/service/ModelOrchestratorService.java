package com.mcp.client.service;

import com.mcp.client.controller.dto.ChatRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.stereotype.Service;

/**
 * Orchestrates the interaction between input validation, the Mistral AI model,
 * and the chat client with MCP tool callbacks.
 */
@Service
public class ModelOrchestratorService {
    private static final String SYSTEM_PROMPT = """
            When presenting Pokemon card results:
            1. For each card, display the information in a clear format
            2. CRITICAL: All image URLs must end with /low.png
            3. CRITICAL: URLs must NOT have any spaces (write https://assets.tcgdex.net/en/base/basep/1/low.png NOT https: //assets.tcgdex.net/en/base/basep/1/low.png)
            4. Format example:
            
               **Card Name**
               - ID: card-id
            
               - ![card-id](https://complete-url/low.png)
            
            5. Present up to 5 cards maximum
            6. Keep URLs as valid links without any spaces or line breaks
            7. If the user's message contains a Pokémon name that is misspelled, correct the name to the closest known Pokémon. If you are unsure, leave it as is.
            """;

    private final MistralService mistralService;
    private final SyncMcpToolCallbackProvider toolCallbackProvider;

    public ModelOrchestratorService(final MistralService mistralService,
                                    final SyncMcpToolCallbackProvider toolCallbackProvider) {
        this.mistralService = mistralService;
        this.toolCallbackProvider = toolCallbackProvider;
    }

    public String invokeMistral(final ChatRequest req) {

        final MistralAiChatModel chatModel = this.mistralService.createMistralChatModel();

        final ChatClient chatClient = ChatClient.builder(chatModel)
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
                .build();

        return chatClient.prompt()
                .user(req.userPrompt())
                .system(SYSTEM_PROMPT)
                .call()
                .content();
    }
}
