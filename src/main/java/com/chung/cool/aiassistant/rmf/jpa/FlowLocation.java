package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "flow_locations")
@Data
public class FlowLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flow_id")
    private Flow flow;

    private String component;

    @Embedded
    private TextRange textRange;

    private String msg;

}
