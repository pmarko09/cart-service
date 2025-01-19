package com.pmarko09.cart_service.mapper;

import com.pmarko09.cart_service.model.dto.CartDto;
import com.pmarko09.cart_service.model.dto.CartItemDto;
import com.pmarko09.cart_service.model.entity.Cart;
import com.pmarko09.cart_service.model.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    @Mapping(source = "cartItems", target = "items", qualifiedByName = "mapCartItemsToDtos")
    CartDto toDto(Cart cart);

    @Named("mapCartItemsToDtos")
    default Set<CartItemDto> mapCartItemsToDtos(Set<CartItem> cartItems) {
        return Optional.ofNullable(cartItems)
                .orElse(Collections.emptySet()).stream()
                .map(this::cartItemToDto)
                .collect(Collectors.toSet());
    }

    CartItemDto cartItemToDto(CartItem cartItem);
}