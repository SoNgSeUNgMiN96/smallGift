package com.sgwannabig.smallgift.springboot.web;

import com.sgwannabig.smallgift.springboot.domain.users.User;
import com.sgwannabig.smallgift.springboot.domain.users.UserReturn;
import com.sgwannabig.smallgift.springboot.mapper.AuthMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AuthNullException extends RuntimeException{

    public AuthNullException(String message){
        super(message);
    }
}


@RestController
public class AuthController {
    private AuthMapper mapper;

    public AuthController(AuthMapper mapper){
        this.mapper = mapper;
    }

    @PostMapping("/api/auth/register")
    public UserReturn register(@RequestBody User user){
        //하나라도 빈 요소가 있다면, Bad request return.
        String hashedPassword = BCrypt.hashpw(user.getPassword(),BCrypt.gensalt());

        Optional<User> isExists =  Optional.ofNullable(mapper.isExists(user.getUsername()));

        if(isExists.isPresent()){
            System.out.println(isExists.get().getUsername()+" is Exists");
            throw new AuthNullException(String.format("%s not found",user.getUsername()));
        }

        if(user.includeNull()){
            throw new AuthNullException(String.format("%s not found",user.getUsername()));
        }

        mapper.regist(user.getUsername(), hashedPassword);
        return new UserReturn(mapper.getId(user.getUsername()), user.getUsername(), 0);
    }
}