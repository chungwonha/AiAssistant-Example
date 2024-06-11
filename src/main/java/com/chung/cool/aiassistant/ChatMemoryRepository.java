package com.chung.cool.aiassistant;

import com.chung.cool.aiassistant.ChatMemory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMemoryRepository extends JpaRepository<ChatMemory, Long> {

    // You can add custom query methods here if needed
//    public List<ChatMemory> findByUserid(String userid);
//    public void deleteByUserid(String userid);

}
