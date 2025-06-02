package com.bite.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//exclude = {SecurityAutoConfiguration.class}
@SpringBootApplication()
public class BiteBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiteBuddyApplication.class, args);
    }

}
