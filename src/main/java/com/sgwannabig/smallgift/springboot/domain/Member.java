package com.sgwannabig.smallgift.springboot.domain;


import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;




@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "Member_Id")
    private long id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;
    private String provider;      //enum Provider
    private String password;
    private String role;
    @Column(name = "email", unique = true)
    private String email;
}
