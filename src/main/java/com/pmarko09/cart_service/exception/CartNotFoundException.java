package com.pmarko09.cart_service.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Long id) {
        super(String.format("Cart with id: %s not found", id));
    }
}
