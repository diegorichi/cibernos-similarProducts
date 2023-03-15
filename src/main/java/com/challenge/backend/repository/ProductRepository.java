package com.challenge.backend.repository;

import com.challenge.backend.domain.Product;
import reactor.core.publisher.Flux;

public interface ProductRepository {

    Flux<Product> getSimilarProducts(long id);
}

