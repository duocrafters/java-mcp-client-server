package com.mcp.frontend.controller;

import com.mcp.frontend.service.AiClientService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final AiClientService aiClientService;
    private final Parser mdParser = Parser.builder().build();
    private final HtmlRenderer mdRenderer = HtmlRenderer.builder().build();

    public HomeController(AiClientService aiClientService) {
        this.aiClientService = aiClientService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("prompt", "");
        model.addAttribute("resultHtml", null);
        model.addAttribute("error", null);
        return "index";
    }

    @PostMapping("/prompt")
    public String submitPrompt(@RequestParam("prompt") String prompt, Model model) {
        model.addAttribute("prompt", prompt);
        try {
            String markdown = aiClientService.ask(prompt);
            Node doc = mdParser.parse(markdown != null ? markdown : "");
            String html = mdRenderer.render(doc);
            model.addAttribute("resultHtml", html);
            model.addAttribute("error", null);
        } catch (Exception e) {
            model.addAttribute("resultHtml", null);
            model.addAttribute("error", "Erreur lors de l'appel au client: " + e.getMessage());
        }
        return "index";
    }
}
