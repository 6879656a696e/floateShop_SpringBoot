package com.woori.demo.controller;

import com.woori.demo.domain.Order;
import com.woori.demo.domain.OrderItem;
import com.woori.demo.domain.User;
import com.woori.demo.dto.UserDto;
import com.woori.demo.service.OrderService;
import com.woori.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/getOrder")
    @ResponseBody
    public List<Order> getOrder(UserDto userDto){
        User user = userService.findUser(userDto.getId());
        return orderService.getOrder(user);
    }

    @GetMapping("/getOrderList")
    @ResponseBody
    public List<OrderItem> getOrderList(Long orderId){
        return orderService.getOrderList(orderId);
    }

    @GetMapping("/getThisOrder")
    @ResponseBody
    public Order getThisOrder(Long orderId){
        return orderService.getThisOrder(orderId);
    }

    @GetMapping("/cancelOrder")
    @ResponseBody
    public void cancelOrder(Long orderId){
        orderService.cancelOrderItem(orderId);
        orderService.cancelOrder(orderId);
    }
}
