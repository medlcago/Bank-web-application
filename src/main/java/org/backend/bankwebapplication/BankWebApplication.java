package org.backend.bankwebapplication;

import org.backend.bankwebapplication.config.secuirity.EnvironmentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class BankWebApplication {
    public static void main(String[] args) {
        EnvironmentConfig.loadEnv();
        SpringApplication.run(BankWebApplication.class, args);
    }
}