package com.woori.demo.repository;

import com.woori.demo.domain.Cart;
import com.woori.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userKey);

    @Query(value="select sum(count) from Cartitem group by cartKey", nativeQuery = true)
    int totalNum();

    //Cart findByIdAndUserUserKey(Long userKey);
}
