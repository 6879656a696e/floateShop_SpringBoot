package com.woori.demo.service;

import com.woori.demo.domain.Order;
import com.woori.demo.domain.OrderItem;
import com.woori.demo.domain.User;
import com.woori.demo.repository.OrderItemRepository;
import com.woori.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public final OrderRepository orderRepository;
    public final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> getOrderList(User user){
        Order order=orderRepository.findOrderByUserId(user.getId());
        return orderItemRepository.findOrderItemByOrderId(order.getId());
    }
}
