package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "issues")
@Data
public class Issue {
    @Id
    private String key;

    private String rule;
    private String severity;
    private String component;
    private String project;
    private Integer line;
    private String hash;
    private String status;

    @Column(length = 1000)
    private String message;

    private String effort;
    private String debt;
    private String author;
    private String creationDate;
    private String updateDate;
    private String type;
    private String scope;
    private Boolean quickFixAvailable;
    private String issueStatus;
    private Boolean prioritizedRule;

    @Embedded
    private TextRange textRange;

    @ElementCollection
    @CollectionTable(name = "issue_tags", joinColumns = @JoinColumn(name = "issue_key"))
    @Column(name = "tag")
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issue")
    private List<Flow> flows;

    @Embedded
    private CleanCode cleanCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "issue")
    private List<Impact> impacts;

}
