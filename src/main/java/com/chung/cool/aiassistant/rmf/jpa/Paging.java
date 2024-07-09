package com.chung.cool.aiassistant.rmf.jpa;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pagings")
@Data
public class Paging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;

}
