package com.sgwannabig.smallgift.springboot.dto.login;


import com.sgwannabig.smallgift.springboot.domain.Provider;
import com.sgwannabig.smallgift.springboot.domain.Role;
import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private Provider provider;
    private Role Role;
}