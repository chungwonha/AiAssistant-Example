package com.chung.cool.aiassistant.rmf.tools;

import com.chung.cool.aiassistant.rmf.agent.CodeRemediationService;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.V;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CodeRemediationTool {

    @Autowired
    CodeRemediationService codeRemediationService;

    @Tool("Remediate vulnerabilities in codes located at {{filePath}} by rewriting the code according to {{instruction}}")
    public String remediateCode(@V("filePath") String filePath, @V("instruction") String instruction) {
        log.info("Remediating code at: " + filePath);
        log.info("Instruction: " + instruction);
        return codeRemediationService.remediate(filePath, instruction);
    }
}
