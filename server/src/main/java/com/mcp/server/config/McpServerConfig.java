package com.mcp.server.config;

import com.mcp.server.tools.PokemonTcgService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpServerConfig {

    @Bean
    public ToolCallbackProvider pokemonTcgTools(PokemonTcgService pokemonTcgService) {
        return MethodToolCallbackProvider.builder().toolObjects(pokemonTcgService).build();
    }
}
