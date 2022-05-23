package com.woori.demo.controller;

import com.woori.demo.domain.Product;
import com.woori.demo.dto.ProductDto;
import com.woori.demo.dto.UserDto;
import com.woori.demo.service.ProductService;
import com.woori.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/admin/addProduct")
    @ResponseBody
    public void addProduct(@RequestBody ProductDto dto) {
        productService.addProduct(dto);
        System.out.println(dto.toString());
    }

    @GetMapping("/productList")
    @ResponseBody
    public List getList() {
        return productService.getProductList();
    }
}