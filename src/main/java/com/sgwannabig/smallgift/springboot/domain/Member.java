package com.sgwannabig.smallgift.springboot.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
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
