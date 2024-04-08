package com.chung.cool.aiassistant;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aiassistant/api")
public class AiAssistantController {
    CurrentTimeAiAssistant currentTimeAiAssistant;
    private final CurrentTimeProvider currentTimeProvider;
    @Value("${openai.api.key}")
    private String openaiApiKey;
    @Autowired
    public AiAssistantController(CurrentTimeProvider currentTimeProvider) {
        this.currentTimeProvider = currentTimeProvider;
    }
    @PostConstruct
    public void init() {
        currentTimeAiAssistant = AiServices.builder(CurrentTimeAiAssistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(openaiApiKey))
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(currentTimeProvider)
                .build();
    }
    @PostMapping("/chat")
    public String chat(@RequestBody String prompt) {
        return currentTimeAiAssistant.chat(prompt);
    }

}
