package com.chung.cool.aiassistant.rmf.agent;

import com.chung.cool.aiassistant.MyPersistentChatMemoryStore;
import com.chung.cool.aiassistant.rmf.tools.CyberDataQueryTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CyberAiAgent {

    private CyberIssueAnalystAgent cyberIssueAnalystAgent;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    CyberDataQueryTool cyberDataQueryTool;
    MyPersistentChatMemoryStore myPersistentChatMemoryStore;

    @Autowired
    public CyberAiAgent(CyberDataQueryTool cyberDataQueryTool, MyPersistentChatMemoryStore myPersistentChatMemoryStore) {
        this.cyberDataQueryTool = cyberDataQueryTool;
        this.myPersistentChatMemoryStore = myPersistentChatMemoryStore;
    }

    @PostConstruct
    public void init() {

        ChatMemoryProvider myChatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(myPersistentChatMemoryStore)
                .build();

        cyberIssueAnalystAgent = AiServices.builder(CyberIssueAnalystAgent.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(openaiApiKey))
                .tools(cyberDataQueryTool)
                .chatMemoryProvider(myChatMemoryProvider)
                .build();
    }


}
