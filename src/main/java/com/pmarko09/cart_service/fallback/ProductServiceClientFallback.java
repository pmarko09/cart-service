package com.pmarko09.cart_service.fallback;

import com.pmarko09.cart_service.client.ProductServiceClient;
import com.pmarko09.cart_service.exception.ProductServiceUnavailableException;
import com.pmarko09.cart_service.model.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceClientFallback implements ProductServiceClient {

    @Override
    public ProductDto getProductById(Long id) {
        throw new ProductServiceUnavailableException("Fallback: unable to get the product." +
                "Product service not available at the moment.");
    }
}
