package com.pmarko09.cart_service.controller;

import com.pmarko09.cart_service.exception.CartNotFoundException;
import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartService cartService;

    @Test
    void createCart_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Starting test: createCart_DataCorrect_ReturnStatus200");
        CartDto cartDto = new CartDto();
        cartDto.setId(1L);
        cartDto.setTotalPrice(99.99);

        when(cartService.createCart()).thenReturn(cartDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalPrice").value(99.99));

        verify(cartService).createCart();
        log.info("Test completed: createCart_DataCorrect_ReturnStatus200");
    }

    @Test
    void addProductToCart_DataCorrect_ReturnStatus200() throws Exception {
        log.info("Starting test: addProductToCart_DataCorrect_ReturnStatus200");
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(3L);
        cartItemDto.setQuantity(5);
        Set<CartItemDto> items = Set.of(cartItemDto);
        CartDto cartDto = new CartDto();
        cartDto.setId(1L);
        cartDto.setItems(items);
        cartDto.setTotalPrice(99.99);

        when(cartService.addItemToCart(1L, 3L, 5)).thenReturn(cartDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/cart/1/addProduct/3?quantity=5")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalPrice").value(99.99))
                .andExpect(jsonPath("$.items[0].productId").value(3L))
                .andExpect(jsonPath("$.items[0].quantity").value(5));

        verify(cartService).addItemToCart(1L, 3L, 5);
        log.info("Test completed: addProductToCart_DataCorrect_ReturnStatus200");
    }

    @Test
    void getCartById_CorrectId_ReturnStatus200() throws Exception {
        log.info("Starting test: getCartById_CorrectId_ReturnStatus200");
        CartDto cartDto = new CartDto();
        cartDto.setId(1L);
        cartDto.setTotalPrice(99.99);

        when(cartService.getCart(1L)).thenReturn(cartDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cart/1")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalPrice").value(99.99));

        verify(cartService).getCart(1L);
        log.info("Test completed: getCartById_CorrectId_ReturnStatus200");
    }

    @Test
    void getCartById_IncorrectId_ReturnStatus404() throws Exception {
        log.info("Starting test: getCartById_IncorrectId_ReturnStatus404");

        when(cartService.getCart(999L)).thenThrow(new CartNotFoundException(999L));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cart/999")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cart with id: 999 not found"));

        verify(cartService).getCart(999L);
        log.info("Test completed: getCartById_IncorrectId_ReturnStatus404");
    }

}
