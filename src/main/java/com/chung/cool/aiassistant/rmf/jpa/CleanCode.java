package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class CleanCode {
    private String attribute;
    private String attributeCategory;

}
