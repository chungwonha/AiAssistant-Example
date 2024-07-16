package com.chung.cool.aiassistant.rmf.tools;

import com.chung.cool.aiassistant.rmf.agent.CodeRemediationService;
import com.chung.cool.aiassistant.rmf.agent.SonarQubeDataService;
import com.chung.cool.aiassistant.rmf.jpa.Issue;
import com.chung.cool.aiassistant.rmf.jpa.Project;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SASTDataQueryTool {

    @Autowired
    CodeRemediationService codeRemediationService;

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

    @Tool("Retrieve cyber issues by severity {{severity}} and project key {{projectKey}} and rewrite the code according to each message or instruction provided.")
    public String queryIssuesBySeverityAndProjectKeyAndRewriteCodeForRemediation(String severity, String projectKey) {
        log.info("Querying issues by severity: " + severity + " and project key: " + projectKey);
        List<Issue> listOfIssues = sonarQubeDataService.findIssuesBySeverity(severity.toUpperCase());

        listOfIssues.forEach(issue -> {
            log.info("Remediating issue: " + issue.getMessage());
            Project project = sonarQubeDataService.findProjectByKey(projectKey);
            String repositoryUrl =  project.getRepositoryUrl();
            String javaFileLocation = this.parseToGetJavFileLocaiton(issue.getComponent());
            String filePath = repositoryUrl + "/" + javaFileLocation;
            log.info("File path: " + filePath);
            // Call remediation service to remediate the code
            String remediatedCode = codeRemediationService.remediate(filePath, issue.getMessage());
            log.info("Remediated code: " + remediatedCode);
            log.info("Remediation completed for issue: " + issue.getMessage());
            log.info("---------------------------------------------------");
        });

        return listOfIssues.size() + " issues remediated.";
    }

    private String parseToGetJavFileLocaiton(String filePath) {
//        String inputString = "sqp_3c00c3e21d8fab313e60cdccf5bac434e602a82a:src/main/java/com/cool/app/newssentimentanalysis/controller/AbstractBaseCsvController.java";
        String inputString = filePath;
        String parsedResult = inputString.substring(inputString.indexOf(":") + 1);

        log.info("Parsed result: " + parsedResult);
        return parsedResult;
    }
}
