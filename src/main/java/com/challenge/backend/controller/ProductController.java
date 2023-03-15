package com.challenge.backend.controller;

import com.challenge.backend.domain.Product;
import com.challenge.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/similar")
    public Mono<ResponseEntity<Flux<Product>>> getSimilarProducts(@PathVariable @Positive long id) {

        Flux<Product> fluxP = productService.getSimilarProducts(id);

        return fluxP.hasElements()
                .flatMap(hasElements -> hasElements
                        ? Mono.just(ResponseEntity.ok(fluxP))
                        : Mono.just(ResponseEntity.notFound().build())
                );

    }

}
