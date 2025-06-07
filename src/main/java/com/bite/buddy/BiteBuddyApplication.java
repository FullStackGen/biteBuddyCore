package com.bite.buddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//exclude = {SecurityAutoConfiguration.class}
@SpringBootApplication()
@EnableCaching
public class BiteBuddyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiteBuddyApplication.class, args);
    }

}
