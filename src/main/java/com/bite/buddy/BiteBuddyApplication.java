package com.bite.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//exclude = {SecurityAutoConfiguration.class}
@SpringBootApplication()
public class BiteBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiteBuddyApplication.class, args);
    }

}
