package com.challenge.backend.service;

import com.challenge.backend.domain.Product;
import com.challenge.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(cacheNames = "similarProducts", key = "{#id}")
    public Flux<Product> getSimilarProducts(long id) {
        return productRepository.getSimilarProducts(id);
    }

    public ProductService(@Qualifier("webApi") ProductRepository productDataSource) {
        this.productRepository = productDataSource;
    }

}





