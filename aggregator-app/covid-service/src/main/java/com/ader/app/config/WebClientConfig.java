package com.ader.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        // Define the buffer size (e.g., 20MB)
        final int bufferSize = 20 * 1024 * 1024; // 20 MB in bytes

        // Create ExchangeStrategies with the increased buffer size
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(bufferSize))
                .build();

        // Return a WebClient.Builder configured with these strategies
        return WebClient.builder()
                .exchangeStrategies(strategies);
    }
}