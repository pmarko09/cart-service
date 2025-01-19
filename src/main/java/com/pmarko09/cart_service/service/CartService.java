package com.pmarko09.cart_service.service;

import com.pmarko09.cart_service.client.ProductServiceClient;
import com.pmarko09.cart_service.mapper.CartMapper;
import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.dto.ProductDto;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.model.entity.CartItem;
import com.pmarko09.cart_service.repository.CartRepository;
import com.pmarko09.cart_service.validation.CartValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductServiceClient productServiceClient;
    private final CartMapper cartMapper;

    @Transactional
    public CartDto createCart() {
        log.info("CartService: creating new cart");
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setCreatedAt(LocalDateTime.now());

        return cartMapper.toDto(cartRepository.save(cart));
    }

    @Transactional
    public CartDto addItemToCart(Long cartId, Long productId, Integer quantity) {
        log.info("CartService: adding product with ID: {} to cart with ID: {}", productId, cartId);
        CartValidation.selectedQuantityCheck(quantity);
        Cart cart = CartValidation.existCheck(cartRepository, cartId);

        ProductDto product = productServiceClient.getProductById(productId);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(productId);
            cartItem.setType(product.getType());
            cartItem.setProductName(product.getName());
            cartItem.setQuantity(quantity);
            cartItem.setProductPrice(product.getPrice());

            cart.getCartItems().add(cartItem);
        }

        Cart.updateCartTotalPrice(cart);
        return cartMapper.toDto(cartRepository.save(cart));
    }

    public CartDto getCart(Long cartId) {
        log.info("CartService: fetching cart with ID: {}", cartId);
        Cart cart = CartValidation.existCheck(cartRepository, cartId);
        return cartMapper.toDto(cart);
    }
}