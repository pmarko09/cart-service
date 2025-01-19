package com.pmarko09.cart_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private Long id;
    private Set<CartItemDto> items;
    private Double totalPrice;
}