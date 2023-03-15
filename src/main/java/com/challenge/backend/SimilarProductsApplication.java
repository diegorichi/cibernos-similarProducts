package com.challenge.backend;

import com.challenge.backend.repository.ProductCacheInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableCaching
public class SimilarProductsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SimilarProductsApplication.class, args);
		context.getBean(ProductCacheInitializer.class);
	}
}


