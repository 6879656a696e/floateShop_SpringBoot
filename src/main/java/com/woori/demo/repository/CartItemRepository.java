package com.woori.demo.repository;

import com.woori.demo.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductProductKey(Long cartKey, Long productKey);
    CartItem findByCartId(Long cartKey);
    List<CartItem> findCartItemByCartId(Long cartKey);
}
