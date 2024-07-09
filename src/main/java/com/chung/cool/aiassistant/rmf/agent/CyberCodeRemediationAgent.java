package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.service.SystemMessage;

public interface CyberCodeRemediationAgent {

    @SystemMessage("You are a senior software engineer.  " +
            "You will be provided with cyber vulnerability issues and instruction on what needs to be fixed in the codes. " +
            "Your job is to re-write the codes to remediate the issues.")

    String remediateCode(String codeSnippet);

}
