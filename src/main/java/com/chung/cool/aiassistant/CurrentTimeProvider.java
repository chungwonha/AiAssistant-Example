package com.chung.cool.aiassistant;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
public class CurrentTimeProvider {
    Logger logger = LoggerFactory.getLogger(CurrentTimeProvider.class);

    @Tool("current timestamp")
    public String getCurrentTimeStamp() {
        log.info("getCurrentTimeStamp called---------------");
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss am");
        String formattedTime = now.format(formatter);

        logger.info("Current Local Time: " + formattedTime);
        return formattedTime;
    }

    @Tool("my timestamp")
    public String getMyTimesatmp(String timeZone) {
        log.info("getMyTimesatmp called---------------");
        LocalTime mytime = LocalTime.now().minus(1, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = mytime.format(formatter);

        logger.info("Current Local Time: " + formattedTime);
        return formattedTime;
    }

    @Tool("current date and time")
    public String getCurrentTimesatmpAndTodayDate() {
        log.info("getCurrentTimesatmpAndTodayDate called---------------");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        String formattedTime = now.format(formatter);

        logger.info("Current Date and Time: " + formattedTime);
        return formattedTime;
    }

//    @Autowired
//    AiAssistantService aiAssistantService;
//    @Tool("bring chats by a given user id number")
//    public String bringChatsByUserId(@P("user id")String userid) {
//        log.info("bringChatsByUserId called---------------user id: "+userid);
//        String results = aiAssistantService.getChatMemory(Long.parseLong(userid));
//        log.info("results: "+results);
//        return results;
//    }
}
