package com.pmarko09.cart_service.controller;

import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public CartDto createCart() {
        log.info("Endpoint POST called: /cart");
        log.info("Creating new cart");
        return cartService.createCart();
    }

    @PostMapping("/{cartId}/addProduct/{productId}")
    public CartDto addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam Integer quantity) {
        log.info("Endpoint POST called: /cart/{}/addProduct/{}", cartId, productId);
        log.info("Adding product with ID: {} to the to the cart with ID: {}", productId, cartId);
        return cartService.addItemToCart(cartId, productId, quantity);
    }

    @GetMapping("/{cartId}")
    public CartDto getCartById(@PathVariable Long cartId) {
        log.info("Endpoint GET called: /cart/{}", cartId);
        log.info("Fetching cart with ID: {}", cartId);
        return cartService.getCart(cartId);
    }
}