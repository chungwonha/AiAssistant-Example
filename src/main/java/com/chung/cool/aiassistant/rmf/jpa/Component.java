package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "components")
@Data
public class Component {
    @Id
    private String key;

    private Boolean enabled;
    private String qualifier;
    private String name;
    private String longName;
    private String path;

}
