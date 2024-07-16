package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CodeRemediationAgent {

    @SystemMessage({"You are a senior software engineer who is specialized in Java.  " +
            "You will be provided with cyber vulnerability issues and instruction on what needs to be fixed in the codes. " +
            "Your job is to re-write the codes to remediate the issues."})


    @UserMessage({"""
            Rewrite the following entire Java code according to this instruction:
            Instruction: {{instruction}}\s

            Code:
             {{entireCode}}\s
            Please provide the entire rewritten code. \

            Please provide the remediated Java code. If you generate any non-Java code or explanations, please format them as comments using /* */ for multi-line comments or // for single-line comments."""})
    String remediateCode(@V("instruction") String instruction, @V("entireCode") String entireCode);

}
