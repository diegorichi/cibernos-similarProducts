package com.challenge.backend.controller;

import com.challenge.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/similar")
    public Mono<ResponseEntity<?>> getSimilarProducts(@PathVariable @Positive long id) {

        return productService.getSimilarProducts(id).collectList()
                .flatMap(list -> !list.isEmpty()
                        ? Mono.just(ResponseEntity.ok(list))
                        : Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"))
                );

    }

}
