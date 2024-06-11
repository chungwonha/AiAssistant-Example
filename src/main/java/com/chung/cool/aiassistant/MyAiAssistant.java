package com.chung.cool.aiassistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface MyAiAssistant {

    String chat(@MemoryId Long id, @UserMessage String prompt);

}
