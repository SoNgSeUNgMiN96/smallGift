package com.sgwannabig.smallgift.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Application {      //testing
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
