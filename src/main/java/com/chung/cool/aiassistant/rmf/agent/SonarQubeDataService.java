package com.chung.cool.aiassistant.rmf.agent;

import com.chung.cool.aiassistant.rmf.jpa.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
@Slf4j
public class SonarQubeDataService {

    @Autowired
    private ScanResultRepository scanResultRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public void saveScanResult(ScanResult scanResult) {
        // Save projects and components first
        scanResult.getIssues().forEach(issue -> {
            Project project = new Project();
            project.setKey(issue.getProject());
            projectRepository.save(project);
            Component component = new Component();
            component.setKey(issue.getComponent());
            componentRepository.save(component);
        });

        // Then save the scan result, which will cascade to issues and other related entities
        scanResultRepository.save(scanResult);
    }

    public void importScanResultFromFile(String filePath) {
        try {
            // Read JSON file content
            String jsonContent = readFileContent(filePath);

            // Parse JSON to ScanResult object
            ScanResult scanResult = objectMapper.readValue(jsonContent, ScanResult.class);

            // Save scan result to database
            saveScanResult(scanResult);

            System.out.println("Scan result imported successfully from: " + filePath);
        } catch (IOException e) {
            System.err.println("Error importing scan result: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String readFileContent(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public List<Issue> findIssuesBySeverity(String severity) {
        List<Issue> issues = issueRepository.findBySeverity(severity);
        log.info("Found " + issues.size() + " issues with severity " + severity);
        log.info("Issues: " + issues);
        return issues;
    }

    public List<Issue> findIssuesByComponent(String component) {
        return issueRepository.findByComponent(component);
    }

    public List<Issue> findAllIssuesByProjectKey(String projectKey) {
        return issueRepository.findAllIssuesByProject(projectKey);
    }

    public List<Issue> findAllIssuesByProjectName(String projectName) {
        Project project = projectRepository.findByName(projectName);
        return this.findAllIssuesByProjectKey(project.getKey());
    }

    public List<Flow> findAllFlowsBySeverity(String severity) {
        List<Issue> issues = this.findIssuesBySeverity(severity);
        return issues.stream()
                .map(Issue::getFlows)
                .flatMap(List::stream)
                .toList();
    }
}
