package org.backend.bankwebapplication.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfig {
    public static void loadEnv() {
        Dotenv dotenv = Dotenv.configure().load();

        String dbURL = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");
        String dadataToken = dotenv.get("DADATA_TOKEN");
        String dadataSecret = dotenv.get("DADATA_SECRET");
        String emailUsername = dotenv.get("EMAIL_USERNAME");
        String emailPassword = dotenv.get("EMAIL_PASSWORD");

        System.setProperty("DB_URL", dbURL);
        System.setProperty("DB_USERNAME", dbUsername);
        System.setProperty("DB_PASSWORD", dbPassword);
        System.setProperty("DADATA_TOKEN", dadataToken);
        System.setProperty("DADATA_SECRET", dadataSecret);
        System.setProperty("EMAIL_USERNAME", emailUsername);
        System.setProperty("EMAIL_PASSWORD", emailPassword);
    }
}
