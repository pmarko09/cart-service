package com.pmarko09.cart_service.config;

import com.pmarko09.cart_service.exception.ResourceNotFoundException;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        switch (response.status()) {
            case 404:
                return new ResourceNotFoundException("Product not found");
            case 400:
                return new BadRequestException("Invalid request to product service");
            case 500, 503:
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        0L,
                        response.request());
            default:
                return new RuntimeException("Error communicating with product service");
        }
    }
}