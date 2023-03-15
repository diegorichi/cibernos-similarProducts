package com.challenge.backend.repository;

import com.challenge.backend.domain.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@Qualifier("webApi")
public class WebClientProductRepository implements ProductRepository {

    @Autowired
    private final WebClient webClient;
    private final Logger log = LoggerFactory.getLogger(WebClientProductRepository.class);

    public WebClientProductRepository(WebClient webClient) {
        this.webClient = webClient;
    }


    @Cacheable(cacheNames = "similarProductsIds", key = "{#id}")
    public Flux<Long> getSimilarProductsIds(long id) {
        return webClient.get()
                .uri("/product/{id}/similarids", id)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Long>() {})
                .onErrorComplete();
    }

    @Cacheable(cacheNames = "similarProducts", key = "{#id}")
    public Flux<Product> getSimilarProducts(long id) {
        return getSimilarProductsIds(id)
                .flatMap(
                        productId -> webClient.get().uri("/product/{productId}",productId ).retrieve()
                                .bodyToMono(Product.class)
                )
                .switchIfEmpty(Flux.empty())
                .doOnError(throwable -> log.error("Error while fetching similar products", throwable));
    }

}


