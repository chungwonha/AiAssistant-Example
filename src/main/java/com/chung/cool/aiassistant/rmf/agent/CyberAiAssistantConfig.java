package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CyberAiAssistantConfig {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Bean
    public OpenAiChatModel openAiChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(openaiApiKey)
                .modelName(OpenAiChatModelName.GPT_4)
                .temperature(0.7)
                .build();
    }

}
