package com.chung.cool.aiassistant.rmf.controller;

import com.chung.cool.aiassistant.rmf.agent.*;

import com.chung.cool.aiassistant.rmf.jpa.Issue;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cyberaiassistant/api")
@Slf4j
public class CyberController {

    @Autowired
    private CodeScanningService codeScanningService;

    @Autowired
    private SonarQubeDataService sonarQubeDataService;

    private CyberIssueAnalystAgent cyberIssueAnalystAgent;

    @Autowired
    public CyberController(CyberAiAgent cyberAiAgent){
          cyberIssueAnalystAgent = cyberAiAgent.getCyberIssueAnalystAgent();
    }

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String,String> prompt) {
        return cyberIssueAnalystAgent.chat(Long.parseLong(prompt.get("userid")),prompt.get("message"));
    }

    @PostMapping("/codescan")
    public String callCodeScanning(@RequestBody Map<String,String> prompt) {
        log.info("callCodeScanning called---------------");
        return codeScanningService.runSonarQube(prompt.get("repositoryUrl"));
    }

    @PostMapping("/save-scan-result")
    public ResponseEntity<String> saveScanResult(@RequestParam("filePath") String filePath) throws JsonProcessingException {
        sonarQubeDataService.importScanResultFromFile(filePath);
        return ResponseEntity.ok("Scan result saved successfully");
    }

    @GetMapping("/issues")
    public ResponseEntity<List<Issue>> getIssuesBySeverity(@RequestParam String severity) {
        List<Issue> issues = sonarQubeDataService.findIssuesBySeverity(severity);
        issues.stream().forEach(issue -> issue.getFlows().forEach(flow -> flow.getLocations().forEach(location -> log.info(location.getTextRange().getStartLine()+" - "+location.getTextRange().getEndLine() +location.getMsg()))));
        return ResponseEntity.ok(issues);
    }


}
