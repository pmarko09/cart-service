package com.pmarko09.cart_service.model.dto;

import com.pmarko09.cart_service.model.entity.ProductType;
import lombok.Data;

@Data
public class CartItemDto {

    private Long productId;
    private String productName;
    private Double productPrice;
    private ProductType type;
    private Integer quantity;
}