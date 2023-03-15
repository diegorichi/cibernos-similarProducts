package com.challenge.backend.repository;

import com.challenge.backend.domain.Product;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Qualifier("webApi")
public class WebClientProductRepository implements ProductRepository {

    @Autowired
    private final WebClient webClient;

    public WebClientProductRepository(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable("similarProductsIds")
    public Flux<Long> getSimilarProductsIds(long id) {
        return webClient.get()
                .uri("/product/{id}/similarids", id)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Long>() {})
                .onErrorComplete()
                .cache(Duration.ofMinutes(10));
    }
    @Cacheable("similarProducts")
    public Flux<Product> getSimilarProducts(long id) {
        return getSimilarProductsIds(id)
                .flatMap(
                        this::getProductById
                )
                .switchIfEmpty(Flux.empty())
                .onErrorComplete()
                .cache(Duration.ofMinutes(10));
    }

    @Cacheable("product")
    private Publisher<Product> getProductById(Long productId) {
        return webClient.get().uri("/product/{productId}",productId ).retrieve()
                .bodyToMono(Product.class)
                .onErrorComplete()
                .cache(Duration.ofMinutes(10));
    }

}


