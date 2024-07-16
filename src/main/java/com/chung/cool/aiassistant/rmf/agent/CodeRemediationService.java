package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: Service to remediate code using AI
 * Author: Chung Ha
 */
@Service
@Slf4j
public class CodeRemediationService {
    private CodeRemediationAgent codeRemediationAgent;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Autowired
    OpenAiChatModel openAiChatModel;

    @PostConstruct
    public void init() {
        log.info("CodeRemediationService initialized.");
        codeRemediationAgent = AiServices.builder(CodeRemediationAgent.class)
                .chatLanguageModel(openAiChatModel)
                .build();
    }

    public String remediate(String filePath, String instruction) {
        try {
            String entireCode = readEntireFile(filePath);
            String remediatedCode = codeRemediationAgent.remediateCode(entireCode, instruction);
            String result2 = extractJavaCode(remediatedCode);
            return saveRemediatedCode(filePath, result2);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public String readEntireFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static String extractJavaCode(String response) {
        List<String> javaCodeBlocks = new ArrayList<>();

        // Regular expression pattern to match Java code blocks
        // This pattern looks for code between ``` markers with "java" specified
        String regex = "```java\\s*(.*?)\\s*```";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String codeBlock = matcher.group(1).trim();
            // Remove square brackets at the beginning and end of the code block
            javaCodeBlocks.add(codeBlock);
        }

        return removeSquareBrackets(javaCodeBlocks.toString());
    }

    private static String removeSquareBrackets(String code) {
        // Remove square brackets at the beginning of the code
        code = code.replaceAll("^\\s*\\[", "");
        // Remove square brackets at the end of the code
        code = code.replaceAll("\\]\\s*$", "");
        return code.trim();
    }

    private String saveRemediatedCode(String filePath, String remediatedCode) throws IOException {
        Files.write(Paths.get(filePath), remediatedCode.getBytes());

        return "Remediation successful. File: "+filePath+" updated.";
    }
}
