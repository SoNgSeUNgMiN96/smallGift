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
    @Column(name = "USER_ID")
    private long id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;
    private String provider;      //enum Provider
    private String password;
    //private String roles;
    private String role;
    @Column(name = "email", unique = true)
    private String email;


    // ENUM으로 안하고 ,로 해서 구분해서 ROLE을 입력 -> 그걸 파싱!!
    /*public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }*/

}
