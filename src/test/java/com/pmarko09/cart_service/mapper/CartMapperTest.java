package com.pmarko09.cart_service.mapper;

import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.model.entity.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class CartMapperTest {

    CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Test
    void mapCartToDto() {
        log.info("Test start: mapCartToDto");
        CartItem cartItem = new CartItem();
        cartItem.setId(5L);
        cartItem.setProductName("ABC");
        cartItem.setQuantity(10);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setTotalPrice(9.99);
        cart.setCartItems(Set.of(cartItem));

        CartDto result = cartMapper.toDto(cart);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(9.99, result.getTotalPrice());
        assertNotNull(result.getItems());

        CartItemDto itemDto = result.getItems().iterator().next();
        assertEquals("ABC", itemDto.getProductName());
        assertEquals(10, itemDto.getQuantity());
        log.info("Test finish: mapCartToDto");
    }
}
