package com.example.minzok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MinzokApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinzokApplication.class, args);
    }

}
