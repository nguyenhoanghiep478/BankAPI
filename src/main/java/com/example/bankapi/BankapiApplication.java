package com.example.bankapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankapiApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("D:\\HiepRepositoryBank\\BankAPI\\").load();
        System.out.println( dotenv.get("DB_USERNAME_R"));
        System.out.println( dotenv.get("DB_PASSWORD"));
        SpringApplication.run(BankapiApplication.class, args);
    }

}
