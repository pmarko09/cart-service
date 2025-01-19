package com.pmarko09.cart_service.mapper;

import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.model.entity.CartItem;
import com.pmarko09.cart_service.model.entity.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CartItemMapperTest {

    CartItemMapper cartItemMapper = Mappers.getMapper(CartItemMapper.class);

    @Test
    void mapCartItemToDto() {
        log.info("Test start: mapCartItemToDto");
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setProductId(5L);
        cartItem.setProductName("AA");
        cartItem.setType(ProductType.COMPUTER);
        cartItem.setQuantity(3);
        cartItem.setProductPrice(5.99);

        CartItemDto result = cartItemMapper.toDto(cartItem);

        assertNotNull(result);
        assertEquals(5L, result.getProductId());
        assertEquals("AA", result.getProductName());
        assertEquals(ProductType.COMPUTER, result.getType());
        assertEquals(3, result.getQuantity());
        assertEquals(5.99, result.getProductPrice());
        log.info("Test finish: mapCartItemToDto");
    }
}
