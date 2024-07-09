package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "impacts")
@Data
public class Impact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_key")
    private Issue issue;

    private String softwareQuality;
    private String severity;

}