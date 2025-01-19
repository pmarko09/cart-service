package com.pmarko09.cart_service.config;

import feign.Client;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.httpclient.ApacheHttpClient;

@Configuration
@Slf4j
public class FeignConfig {

    @Bean
    public Client feignClient() {
        log.info("Creating feignClient bean");
        return new ApacheHttpClient();
    }

    @Bean
    public Retryer feignRetryer() {
        log.info("Creating feignRetryer bean");
        return new Retryer.Default(1000, 2000, 3) {
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        log.info("Creating errorDecoder bean");
        return new CustomErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
