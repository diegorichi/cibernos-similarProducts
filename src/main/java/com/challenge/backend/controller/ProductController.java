package com.challenge.backend.controller;

import com.challenge.backend.domain.Product;
import com.challenge.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Positive;
import java.util.List;

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

        Mono<List<Product>> fluxP =  productService.getSimilarProducts(id).collectList();

        return fluxP
                .flatMap(list -> !list.isEmpty()
                        ? Mono.just(ResponseEntity.ok(list))
                        : Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"))
                );

    }

}
