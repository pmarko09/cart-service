package com.pmarko09.cart_service.service;

import com.pmarko09.cart_service.client.ProductServiceClient;
import com.pmarko09.cart_service.exception.CartNotFoundException;
import com.pmarko09.cart_service.mapper.CartMapper;
import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.model.dto.ProductDto;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.model.entity.ProductType;
import com.pmarko09.cart_service.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.cloud.contract.wiremock.restdocs.WireMockWebTestClient.verify;

@Slf4j
public class CartServiceTest {

    ProductServiceClient productServiceClient;
    CartService cartService;
    CartRepository cartRepository;
    CartMapper cartMapper;

    @BeforeEach
    void setup() {
        this.productServiceClient = Mockito.mock(ProductServiceClient.class);
        this.cartRepository = Mockito.mock(CartRepository.class);
        this.cartMapper = Mappers.getMapper(CartMapper.class);
        this.cartService = new CartService(cartRepository, productServiceClient, cartMapper);
    }

    @Test
    void createCart_DataCorrect_CartDtoReturned() {
        log.info("Test start: createCart_DataCorrect_CartDtoReturned");
        Cart cart = new Cart();
        cart.setId(10L);
        cart.setTotalPrice(2.01);
        cart.setCreatedAt(LocalDateTime.of(2025, 10, 10, 10, 1));

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto result = cartService.createCart();

        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(2.01, result.getTotalPrice());

        Mockito.verify(cartRepository).save(any(Cart.class));
        log.info("Test finish: createCart_DataCorrect_CartDtoReturned");
    }

    @Test
    void addItemToCart_DataCorrect_CartDtoReturned() {
        log.info("Test start: addItemToCart_DataCorrect_CartDtoReturned");
        Integer quantity = 5;

        Cart cart = new Cart();
        cart.setId(3L);
        cart.setTotalPrice(0.0);
        cart.setCreatedAt(LocalDateTime.of(2025, 10, 10, 10, 1));

        ProductDto productDto = new ProductDto();
        productDto.setId(10L);
        productDto.setName("A");
        productDto.setPrice(99.9);
        productDto.setType(ProductType.COMPUTER);

        when(cartRepository.findById(3L)).thenReturn(Optional.of(cart));
        when(productServiceClient.getProductById(10L)).thenReturn(productDto);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        CartDto result = cartService.addItemToCart(3L, 10L, quantity);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        CartItemDto item = result.getItems().iterator().next();
        assertEquals(10L, item.getProductId());
        assertEquals("A", item.getProductName());
        assertEquals(ProductType.COMPUTER, item.getType());
        assertEquals(productDto.getPrice() * quantity, result.getTotalPrice());

        Mockito.verify(cartRepository).findById(cart.getId());
        Mockito.verify(productServiceClient).getProductById(productDto.getId());
        Mockito.verify(cartRepository).save(any(Cart.class));
        log.info("Test finish: addItemToCart_DataCorrect_CartDtoReturned");
    }

    @Test
    void getCart_DataCorrect_CartDtoReturned() {
        log.info("Test start: getCart_DataCorrect_CartDtoReturned");
        Cart cart = new Cart();
        cart.setId(3L);
        cart.setTotalPrice(0.0);
        cart.setCreatedAt(LocalDateTime.of(2025, 10, 10, 10, 1));

        when(cartRepository.findById(3L)).thenReturn(Optional.of(cart));

        CartDto result = cartService.getCart(3L);

        assertNotNull(result);
        assertEquals(3L, result.getId());
        assertEquals(0.0, result.getTotalPrice());

        Mockito.verify(cartRepository).findById(3L);
        log.info("Test finish: getCart_DataCorrect_CartDtoReturned");
    }

    @Test
    void getCart_CartNotFound_ThrowException() {
        log.info("Test start: getCart_CartNotFound_ThrowException");
        when(cartRepository.findById(5L)).thenReturn(Optional.empty());

        CartNotFoundException aThrows = assertThrows(CartNotFoundException.class, () ->
                cartService.getCart(5L));

        assertEquals(aThrows.getMessage(), "Cart with id: 5 not found");
        log.info("Test finish: getCart_CartNotFound_ThrowException");
    }
}