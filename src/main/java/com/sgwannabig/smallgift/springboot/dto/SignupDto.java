package com.sgwannabig.smallgift.springboot.dto;

import com.sgwannabig.smallgift.springboot.domain.enumerate.Role;
import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String email;
    //private Provider provider;
    private Role Role;
}