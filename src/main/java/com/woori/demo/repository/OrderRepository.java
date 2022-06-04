package com.woori.demo.repository;

import com.woori.demo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value="select sum(itemTotalPrice) from orderitem group by orderKey", nativeQuery = true)
    Integer totalItemPrice();

    Order findOrderByUserId(long userKey);
}
