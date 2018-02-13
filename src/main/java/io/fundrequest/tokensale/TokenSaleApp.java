package io.fundrequest.tokensale;

import io.fundrequest.tokensale.notification.participant.mailchimp.AddSubscriberCommand;
import io.fundrequest.tokensale.notification.participant.mailchimp.MailChimpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TokenSaleApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TokenSaleApp.class, args);
        MailChimpClient client = run.getBean(MailChimpClient.class);
        AddSubscriberCommand command = new AddSubscriberCommand();
        command.setFirstName("Davy");
        command.setLastName("Van Roy");
        command.setEmail("davy.van.roy" + UUID.randomUUID().getLeastSignificantBits() + "@gmail.com");
        command.setTokenAmount(BigDecimal.TEN);
        client.addSubscriber(command);
    }
}