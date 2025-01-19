package com.pmarko09.cart_service.client;

import com.pmarko09.cart_service.config.FeignConfig;
import com.pmarko09.cart_service.fallback.ProductServiceClientFallback;
import com.pmarko09.cart_service.model.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",
        url = "${spring.cloud.openfeign.client.config.productServiceClient.url}",
        configuration = FeignConfig.class,
        fallback = ProductServiceClientFallback.class)
public interface ProductServiceClient {

    @GetMapping("/products/id/{id}")
    ProductDto getProductById(@PathVariable Long id);
}