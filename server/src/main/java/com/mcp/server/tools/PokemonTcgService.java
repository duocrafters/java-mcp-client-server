package com.mcp.server.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class PokemonTcgService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonTcgService.class);
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "https://api.tcgdex.net/v2/en/cards?name=";

    @Tool(name = "search_pokemon", description = "Search Pokemon cards by name")
    public String pokemonByName(@ToolParam(description = "Pokemon's name") final String name) throws IOException, InterruptedException {
        LOGGER.info("Looking for : {}", name);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + name))
                .GET()
                .build();

        var response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body() != null ? response.body() : String.format("Aucune carte trouvée pour %s", name);
    }
}
