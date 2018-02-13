package io.fundrequest.tokensale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TokenSaleApp {

    public static void main(String[] args) {
        SpringApplication.run(TokenSaleApp.class, args);
    }
}