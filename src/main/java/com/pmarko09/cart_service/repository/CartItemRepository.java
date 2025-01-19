package com.pmarko09.cart_service.repository;

import com.pmarko09.cart_service.model.entity.CartItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c")
    List<CartItem> findAllCartItems(Pageable pageable);
}