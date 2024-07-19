package com.example.bankapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class BankapiApplication {


    public static void main(String[] args) {
        SpringApplication.run(BankapiApplication.class, args);

    }

}
