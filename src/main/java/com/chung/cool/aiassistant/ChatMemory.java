package com.chung.cool.aiassistant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class ChatMemory {
    @Id
    private Long id;

    @Lob
    private String message;

    // Constructors, getters, and setters
}

