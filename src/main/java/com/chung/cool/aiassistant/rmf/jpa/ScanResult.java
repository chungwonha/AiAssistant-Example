package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "scan_results")
@Data
public class ScanResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer total;
    private Integer p;
    private Integer ps;
    private Integer effortTotal;

    @OneToOne(cascade = CascadeType.ALL)
    private Paging paging;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Issue> issues;
}
