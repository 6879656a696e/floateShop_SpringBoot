package com.woori.demo.service;

import com.woori.demo.domain.CartItem;
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

    public List<Order> getOrder(User user){
        return orderRepository.findOrderByUserId(user.getId());
    }

    public List<OrderItem> getOrderList(Long orderId){
        return orderItemRepository.findOrderItemByOrderId(orderId);
    }

    public Order getThisOrder(Long orderId){
        return orderRepository.findOrderById(orderId);
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOrderById(orderId);
        orderRepository.delete(order);
    }

    public void cancelOrderItem(Long orderId){
        List<OrderItem> orderItem= orderItemRepository.findOrderItemByOrderId(orderId);
        for(OrderItem oi : orderItem){
            orderItemRepository.delete(oi);
        }
    }
}
