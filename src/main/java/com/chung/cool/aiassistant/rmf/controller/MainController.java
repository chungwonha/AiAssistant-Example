package com.chung.cool.aiassistant.rmf.controller;

import com.chung.cool.aiassistant.rmf.agent.SonarQubeDataService;
import com.chung.cool.aiassistant.rmf.jpa.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    SonarQubeDataService sonarQubeDataService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/sonarqube")
    public String sonarqube(Model model) {
        // Add logic to fetch findings and add to model
        // model.addAttribute("findings", findings);
        List<Issue> findings = sonarQubeDataService.findIssuesBySeverity("CRITICAL");
        model.addAttribute("findings", findings);
        return "sonarqube";
    }
}
