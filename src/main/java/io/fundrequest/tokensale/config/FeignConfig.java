package io.fundrequest.tokensale.config;

import io.fundrequest.tokensale.TokenSaleApp;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = {TokenSaleApp.class})
public class FeignConfig {
}

