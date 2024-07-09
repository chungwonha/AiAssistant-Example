package com.chung.cool.aiassistant.rmf.agent;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CyberWorkerService {

    private final ChatLanguageModel model;
    private final CodeScanningService codeScanningService;
    private final VulnerabilityRecordingService vulnerabilityRecordingService;

    public CyberWorkerService(@Value("${openai.api.key}") String openAiApiKey,
                              CodeScanningService codeScanningService,
                              VulnerabilityRecordingService vulnerabilityRecordingService) {
        this.model = OpenAiChatModel.builder()
                .apiKey(openAiApiKey)
                .build();
        this.codeScanningService = codeScanningService;
        this.vulnerabilityRecordingService = vulnerabilityRecordingService;
    }

    public String performScanAndReport(String repositoryUrl) {
        String scanResults = codeScanningService.scanCode(repositoryUrl);
        vulnerabilityRecordingService.recordFindings(scanResults);

        return model.generate("Generate a comprehensive report on cyber vulnerabilities based on these findings, " +
                "including an executive summary and detailed analysis: " + scanResults);
    }
}