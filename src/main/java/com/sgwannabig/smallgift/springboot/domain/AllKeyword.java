package com.sgwannabig.smallgift.springboot.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class AllKeyword extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "allKeyword_id")
    private long id;

    String keyword;

    int count;
}
