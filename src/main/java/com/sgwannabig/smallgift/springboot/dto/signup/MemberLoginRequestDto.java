package com.sgwannabig.smallgift.springboot.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequestDto {
    private String username;
    private String email;
    private String password;
}
