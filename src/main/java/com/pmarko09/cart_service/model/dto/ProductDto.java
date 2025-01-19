package com.pmarko09.cart_service.model.dto;

import com.pmarko09.cart_service.model.entity.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Double price;
    private ProductType type;
}
