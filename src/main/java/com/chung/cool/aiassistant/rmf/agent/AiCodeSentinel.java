package com.chung.cool.aiassistant.rmf.agent;

import com.chung.cool.aiassistant.MyPersistentChatMemoryStore;
import com.chung.cool.aiassistant.rmf.tools.CodeRemediationTool;
import com.chung.cool.aiassistant.rmf.tools.SASTDataQueryTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Description:
 */
@Component
@Data
public class AiCodeSentinel {

    private SASTAnalystAgent SASTAnalystAgent;


    @Value("${openai.api.key}")
    private String openaiApiKey;

    SASTDataQueryTool SASTDataQueryTool;
    CodeRemediationTool codeRemediationTool;
    MyPersistentChatMemoryStore myPersistentChatMemoryStore;

    @Autowired
    OpenAiChatModel openAiChatModel;

    @Autowired
    public AiCodeSentinel(SASTDataQueryTool SASTDataQueryTool,
                          CodeRemediationTool codeRemediationTool,
                          MyPersistentChatMemoryStore myPersistentChatMemoryStore) {
        this.SASTDataQueryTool = SASTDataQueryTool;
        this.codeRemediationTool = codeRemediationTool;
        this.myPersistentChatMemoryStore = myPersistentChatMemoryStore;
    }

    @PostConstruct
    public void init() {

        ChatMemoryProvider myChatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(myPersistentChatMemoryStore)
                .build();

        SASTAnalystAgent = AiServices.builder(SASTAnalystAgent.class)
                .chatLanguageModel(openAiChatModel)
                .tools(SASTDataQueryTool)
                .chatMemoryProvider(myChatMemoryProvider)
                .build();

    }


}
