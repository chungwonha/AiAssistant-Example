package com.chung.cool.aiassistant;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ChatMemoryTools {
    @Autowired
    AiAssistantService aiAssistantService;
    @Tool("bring chats by a given user id number")
    public String bringChatsByUserId(@P("user id")String userid) {
        log.info("bringChatsByUserId called---------------user id: "+userid);
        String results = aiAssistantService.getChatMemory(Long.parseLong(userid));
        log.info("results: "+results);
        return results;
    }
}
