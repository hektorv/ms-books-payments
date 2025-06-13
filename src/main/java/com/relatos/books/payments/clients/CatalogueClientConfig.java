package com.relatos.books.payments.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class CatalogueClientConfig {
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient catalogueRestClient(RestClient.Builder builder, @Value("${catalogue.base-url}") String baseUrl) {
        
        return builder
                .baseUrl(baseUrl)
                .build();
    }    
}
