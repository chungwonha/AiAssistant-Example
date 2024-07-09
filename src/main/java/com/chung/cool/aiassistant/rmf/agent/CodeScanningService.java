package com.chung.cool.aiassistant.rmf.agent;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;

@Service
@Slf4j
public class CodeScanningService {

    @Value("sonarqube.project.key")
    String sonarqubeProjectKey;
    @Value("sonarqube.host.url")
    String sonarHostUrl = "";
    @Value("sonarqube.token")
    String sonarToken;
    @Value("sonarqube.results.path")
    String sonarqubeResultsPath;
    @Value("sonarqube.project.name")
    String sonarqubeProjectName;

    @Value("${maven.home}")
    private String mavenHome;

    private final RestTemplate restTemplate = new RestTemplate();

    public String scanCode(String repositoryUrl) {
        // Here you would implement the logic to clone the repository and run SonarQube
        log.info("Scanning code repository at: " + repositoryUrl);
        String sonarQubeResults = runSonarQube(repositoryUrl);

        return sonarQubeResults;
    }

    public String runSonarQube(String localPath) {
        try {

            // Convert URI to Path if necessary
            Path codePath;
            if (localPath.startsWith("file:")) {
                codePath = Paths.get(URI.create(localPath));
            } else {
                codePath = Paths.get(localPath);
            }

            // Verify the path exists
            if (!Files.exists(codePath) || !Files.isDirectory(codePath)) {
                throw new IllegalArgumentException("Invalid local path: " + localPath);
            }
            log.info("codePath: " + codePath.toAbsolutePath());

            String mvnCommand = Paths.get(mavenHome, "bin", "mvn.cmd").toString();

//
            ProcessBuilder processBuilder = new ProcessBuilder(
                    mvnCommand, "clean", "compile", "sonar:sonar",
                    "-Dsonar.projectKey=" + sonarqubeProjectKey,
                    "-Dsonar.host.url=" + sonarHostUrl,
                    "-Dsonar.login=" + sonarToken
            );
            processBuilder.directory(codePath.toFile());
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            log.info("SonarQube analysis output: " + output.toString());

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("SonarQube analysis failed. Exit code: " + exitCode + "\nOutput: " + output.toString());
            }

            // Fetch results from SonarQube API
            String sonarQubeResults = fetchSonarQubeResults(sonarqubeProjectKey);

            // Save results to file
//            String resultFilePath = saveResultsToFile(sonarQubeResults);
            String resultFilePath = exportSonarQubeResults();

            return "SonarQube analysis completed successfully. Results saved to: " + resultFilePath;

        } catch (Exception e) {
            throw new RuntimeException("Error running SonarQube analysis", e);
        }
    }

    private String saveResultsToFile(String results) throws IOException {
        // Generate file name with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "sonarqube_results_" + timestamp + ".txt";
        Path filePath = Paths.get(sonarqubeResultsPath, fileName);

        // Ensure the directory exists
        Files.createDirectories(filePath.getParent());

        // Write results to file
        Files.write(filePath, results.getBytes(), StandardOpenOption.CREATE);

        return filePath.toString();
    }

    public String exportSonarQubeResults() throws IOException {
        String apiUrl = String.format("%s/api/issues/search?componentKeys=%s", sonarHostUrl, sonarqubeProjectKey);

        // Set up headers for authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + sonarToken);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Make the API call
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String results = response.getBody();
            String fileName = saveResultsToFile(results);
            return fileName;
        } else {
            throw new RuntimeException("Failed to fetch SonarQube results. Status code: " + response.getStatusCodeValue());
        }
    }

    private String fetchSonarQubeResults(String projectKey) {
        // Implement API call to SonarQube to fetch results
        // This is a placeholder and should be replaced with actual API call
        return "Placeholder for SonarQube results for project: " + projectKey;
    }
}
