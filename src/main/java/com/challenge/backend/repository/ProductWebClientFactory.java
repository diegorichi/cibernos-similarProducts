package com.challenge.backend.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
public class ProductWebClientFactory {
    @Value("${productWebclient.readTimeoutMillis}")
    private int readTimeoutMillis;

    @Value("${productWebclient.followRedirects}")
    private boolean followRedirects;
    @Value("${productWebclient.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient productWebClient() {
        HttpClient httpClient = HttpClient.create()
                .followRedirect(followRedirects)
                .baseUrl(baseUrl)
                .responseTimeout(java.time.Duration.ofMillis(readTimeoutMillis));


        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

