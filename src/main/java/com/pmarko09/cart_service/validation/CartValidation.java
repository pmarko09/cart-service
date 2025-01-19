package com.pmarko09.cart_service.validation;

import com.pmarko09.cart_service.exception.CartNotFoundException;
import com.pmarko09.cart_service.exception.IllegalQuantityException;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.repository.CartRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CartValidation {

    public static Cart existCheck(CartRepository cartRepository, Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    public static void selectedQuantityCheck(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalQuantityException("Chosen quantity must be at least 1");
        } else if (quantity > 10) {
            throw new IllegalQuantityException("Chosen quantity can not be higher than 10");
        }
    }
}