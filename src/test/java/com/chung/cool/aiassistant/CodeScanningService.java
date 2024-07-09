package com.chung.cool.aiassistant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class CodeScanningService {

//    @InjectMocks
//    private com.chung.cool.aiassistant.rmf.agent.CodeScanningService codeScanningAgent;
//
//    @Mock
//    private Runtime runtime;
//
//    @TempDir
//    Path tempDir;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(codeScanningAgent, "sonarHostUrl", "http://localhost:9000");
//        ReflectionTestUtils.setField(codeScanningAgent, "sonarToken", "dummy-token");
//        ReflectionTestUtils.setField(codeScanningAgent, "projectKey", "test-project");
//        ReflectionTestUtils.setField(codeScanningAgent, "resultsPath", tempDir.toString());
//        ReflectionTestUtils.setField(codeScanningAgent, "mavenHome", "C:/Program Files/Apache/apache-maven-3.9.8");
//    }
//
//    @Test
//    void testRunSonarQubeSuccess() throws Exception {
//        // Mock Process
//        Process process = mock(Process.class);
//        when(process.waitFor()).thenReturn(0);
//        when(process.getInputStream()).thenReturn(new java.io.ByteArrayInputStream("Analysis successful".getBytes()));
//
//        // Mock Runtime
//        when(runtime.exec(any(String[].class), any(), any())).thenReturn(process);
//
//        // Test
//        String result = codeScanningAgent.runSonarQube(tempDir.toString());
//
//        assertTrue(result.contains("SonarQube analysis completed successfully"));
//        assertTrue(result.contains("Results saved to:"));
//    }
//
//    @Test
//    void testRunSonarQubeFailure() throws Exception {
//        // Mock Process
//        Process process = mock(Process.class);
//        when(process.waitFor()).thenReturn(1);
//        when(process.getInputStream()).thenReturn(new java.io.ByteArrayInputStream("Analysis failed".getBytes()));
//
//        // Mock Runtime
//        when(runtime.exec(any(String[].class), any(), any())).thenReturn(process);
//
//        // Test
//        assertThrows(RuntimeException.class, () -> codeScanningAgent.runSonarQube(tempDir.toString()));
//    }
//
//    @Test
//    void testRunSonarQubeInvalidPath() {
//        assertThrows(IllegalArgumentException.class, () -> codeScanningAgent.runSonarQube("invalid/path"));
//    }
//
//    @Test
//    void testRunSonarQubeIOException() throws Exception {
//        when(runtime.exec(any(String[].class), any(), any())).thenThrow(new IOException("Command execution failed"));
//
//        assertThrows(RuntimeException.class, () -> codeScanningAgent.runSonarQube(tempDir.toString()));
//    }
//
//    @Test
//    void testRunSonarQubeInterruption() throws Exception {
//        // Mock Process
//        Process process = mock(Process.class);
//        when(process.waitFor()).thenThrow(new InterruptedException("Process interrupted"));
//
//        // Mock Runtime
//        when(runtime.exec(any(String[].class), any(), any())).thenReturn(process);
//
//        assertThrows(RuntimeException.class, () -> codeScanningAgent.runSonarQube(tempDir.toString()));
//    }
}
