package com.example.enter_school_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EnterSchoolManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnterSchoolManagementApplication.class, args);
    }

}
