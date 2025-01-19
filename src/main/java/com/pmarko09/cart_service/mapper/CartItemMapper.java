package com.pmarko09.cart_service.mapper;

import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.model.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);
}