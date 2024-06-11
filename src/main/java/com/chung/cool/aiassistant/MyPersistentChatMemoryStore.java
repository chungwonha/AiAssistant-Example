package com.chung.cool.aiassistant;

import dev.langchain4j.data.message.ChatMessage;

import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

@Component
@Slf4j
public class MyPersistentChatMemoryStore implements ChatMemoryStore {

    @Autowired
    ChatMemoryRepository chatMemoryRepository;

    @Override
    public List<ChatMessage> getMessages(Object userid) {
        log.info("getMessages called");
         Optional<ChatMemory> chatMemorie = chatMemoryRepository.findById((long)userid);
         if(chatMemorie.isEmpty()){
             return new ArrayList<>();
         }else {
             return messagesFromJson(chatMemorie.get().getMessage());
         }
    }

    @Override
    public void updateMessages(Object userid, List<ChatMessage> messages) {
        log.info("updateMessages called");
        ChatMemory chatMemory = new ChatMemory();
        chatMemory.setMessage(messagesToJson(messages));
        chatMemory.setId((long)userid);
        chatMemoryRepository.save(chatMemory);
    }

    @Override
    public void deleteMessages(Object userid) {
        log.info("deleteMessages called");
        chatMemoryRepository.deleteById((long)userid);
    }
}
