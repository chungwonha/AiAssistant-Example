package com.chung.cool.aiassistant.rmf.tools;

import com.chung.cool.aiassistant.rmf.agent.SonarQubeDataService;
import com.chung.cool.aiassistant.rmf.jpa.Issue;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CyberDataQueryTool {

//    @Autowired
//    CodeScanningService codeScanningService;

    @Autowired
    SonarQubeDataService sonarQubeDataService;

//    @Tool("scan code repository for vulnerabilities")
//    public String scanCode(@P("The location of code repository to scan") String repositoryUrl) {
//        // Here you would implement the logic to clone the repository and run SonarQube
//        log.info("Scanning code repository at: " + repositoryUrl);
//        String sonarQubeResults = codeScanningService.scanCode(repositoryUrl);
//
//        return sonarQubeResults;
//    }

    @Tool("Query cyber issues by severity")
    public List<Issue> queryIssuesBySeverity(String severity) {
        return sonarQubeDataService.findIssuesBySeverity(severity.toUpperCase());
    }

    @Tool("Retrieve all cyber issues by cyber project")
    public List<Issue> queryAllIssuesByProjectKey(String projectKey) {
        return sonarQubeDataService.findAllIssuesByProjectKey(projectKey);
    }
}
