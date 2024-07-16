package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "projects")
@Data
public class Project {
    @Id
    private String key;

    private String name;

    private String repositoryUrl;
}
