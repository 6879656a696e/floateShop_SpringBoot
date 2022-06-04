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

    @PostMapping("/getOrderList")
    @ResponseBody
    public List<OrderItem> getOrderList(UserDto userDto){
        User user = userService.findUser(userDto.getId());
        return orderService.getOrderList(user);
    }
}
