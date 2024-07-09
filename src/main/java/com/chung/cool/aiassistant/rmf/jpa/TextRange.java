package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class TextRange {

    private Integer startLine;
    private Integer endLine;
    private Integer startOffset;
    private Integer endOffset;

}
