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

    @Column(unique = true, nullable = false)  //unique설정이 필요함, 빈 값은 허용하지 않음.
    String keyword;

    @Column(nullable = false)  //빈 값은 허용 X
    int count;
}
