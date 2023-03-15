package com.challenge.backend.repository;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConditionalOnProperty("prefetchCache")
public class ProductCacheInitializer {
    private final Logger log = LoggerFactory.getLogger(ProductCacheInitializer.class);

    @Autowired
    ProductRepository repository;

    @Value("${prefetchCacheValues}")
    Long[] prefetchCacheValues;
    @PostConstruct
    public void initialize() {
        for (int i = 0; i < prefetchCacheValues.length; i++) {
            repository.getSimilarProducts(prefetchCacheValues[i]).cache(Duration.ofMinutes(60))
                    .doOnComplete(() ->
                        log.info("prefetch finished")
                    )
                    .subscribe();
        }
    }
}
