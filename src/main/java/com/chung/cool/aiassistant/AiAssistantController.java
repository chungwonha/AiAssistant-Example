package com.chung.cool.aiassistant;

import com.chung.cool.aiassistant.rmf.tools.CyberDataQueryTool;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Map;
import java.util.Optional;

/**
 * Class: AiAssistantController
 * Author: Chung Ha
 * Date: 2023-03-06
 * Description: This controller class handles the API endpoints for the AI assistant.
 */

@RestController
@RequestMapping("/aiassistant/api")
@Slf4j
public class AiAssistantController {
    CurrentTimeAiAssistant currentTimeAiAssistant;
    @Autowired
    MyPersistentChatMemoryStore myPersistentChatMemoryStore;
    @Autowired
    ChatMemoryRepository chatMemoryRepository;
    private MyAiAssistant myAiAssistant;
    private final CurrentTimeProvider currentTimeProvider;
    private final ChatMemoryTools chatMemoryTools;
    @Autowired
    private AiAssistantService aiAssistantService;
    @Value("${openai.api.key}")
    private String openaiApiKey;
    @Value("${file.upload-dir}")
    private String uploadDir;

    private CyberDataQueryTool cyberDataQueryTool;

    @Autowired
    public AiAssistantController(CurrentTimeProvider currentTimeProvider, ChatMemoryTools chatMemoryTools, CyberDataQueryTool cyberDataQueryTool) {
        this.currentTimeProvider = currentTimeProvider;
        this.chatMemoryTools = chatMemoryTools;
        this.cyberDataQueryTool = cyberDataQueryTool;
    }
    @PostConstruct
    public void init() {

        currentTimeAiAssistant = AiServices.builder(CurrentTimeAiAssistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(openaiApiKey))
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(currentTimeProvider)
                .build();

        ChatMemoryProvider myChatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(myPersistentChatMemoryStore)
                .build();

        myAiAssistant = AiServices.builder(MyAiAssistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(openaiApiKey))
                .chatMemoryProvider(myChatMemoryProvider)
                .tools(currentTimeProvider,chatMemoryTools, cyberDataQueryTool)
                .build();
    }



    @PostMapping("/chat")
    public String chat(@RequestBody Map<String,String> prompt) {
        return myAiAssistant.chat(Long.parseLong(prompt.get("userid")),prompt.get("message"));
    }

    @GetMapping("/getmemory/{userid}")
    public String getMemory(@PathVariable String userid){
        Optional<ChatMemory> chatMemory = chatMemoryRepository.findById(Long.parseLong(userid));
        return chatMemory.isEmpty() ?"none":chatMemory.get().getMessage();
    }
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("upload called---------------");
        try {
            byte[] bytes = file.getBytes();
            log.info("uploadDir: "+uploadDir+file.getOriginalFilename());
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(path, bytes);
            return "File uploaded successfully";
        } catch (IOException e) {
            // Handle the exception appropriately in your application
            return "Failed to upload file";
        }
    }
    @PostMapping("/embeddingstore")
    public String embeddingStore(@RequestParam("fileName") String fileName) {
        aiAssistantService.embeddingStore(fileName);
        return "Embedding store completed";
    }
    @PostMapping("/conversationalchat")
    public String conversationalChat(@RequestBody String prompt) {
        return aiAssistantService.chat(prompt);
    }
}
