package com.woori.demo.repository;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductProductKey(Long cartKey, Long productKey);
}
