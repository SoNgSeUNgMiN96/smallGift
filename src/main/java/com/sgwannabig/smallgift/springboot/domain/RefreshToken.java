package com.sgwannabig.smallgift.springboot.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @Builder
    public RefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public  void update(String RefreshToken){
        this.refreshToken = refreshToken;
    }


}
