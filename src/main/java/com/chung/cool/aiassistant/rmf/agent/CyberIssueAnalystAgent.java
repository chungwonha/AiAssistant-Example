package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface CyberIssueAnalystAgent {

    //String chat(@MemoryId Long id, @UserMessage String prompt);
    String chat(@MemoryId Long id, @UserMessage String prompt);

}
