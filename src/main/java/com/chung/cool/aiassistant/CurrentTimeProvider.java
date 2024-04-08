package com.chung.cool.aiassistant;

import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class CurrentTimeProvider {
    Logger logger = LoggerFactory.getLogger(CurrentTimeProvider.class);

    @Tool
    public String getCurrentTimeStamp() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss am");
        String formattedTime = now.format(formatter);

        logger.info("Current Local Time: " + formattedTime);
        return formattedTime;
    }

    @Tool
    public String getMyTimesatmp(String timeZone) {
        LocalTime mytime = LocalTime.now().minus(1, ChronoUnit.HOURS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String formattedTime = mytime.format(formatter);

        logger.info("Current Local Time: " + formattedTime);
        return formattedTime;
    }
}
