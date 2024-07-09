package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "flows")
@Data
public class Flow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_key")
    private Issue issue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flow")
    private List<FlowLocation> locations;

}
