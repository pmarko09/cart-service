package com.pmarko09.cart_service.exception;

public class IllegalQuantityException extends RuntimeException {
    public IllegalQuantityException(String message) {
        super(message);
    }
}
