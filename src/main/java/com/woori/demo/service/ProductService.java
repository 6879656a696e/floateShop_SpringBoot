package com.woori.demo.service;

import com.woori.demo.domain.Authority;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.dto.ProductDto;
import com.woori.demo.dto.UserDto;
import com.woori.demo.repository.ProductRepository;
import com.woori.demo.repository.UserRepository;
import com.woori.demo.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Product addProduct(ProductDto dto) {
        Product product=Product.builder()
                .product_name(dto.getProduct_name())
                .product_category(dto.getProduct_category())
                .product_cnt(dto.getProduct_cnt())
                .product_desc(dto.getProduct_desc())
                .product_pic(dto.getProduct_pic())
                .product_price(dto.getProduct_price())
                .product_num(dto.getProduct_num())
                .activated(true)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getProductList(){
//        productRepository.findAll().forEach(p->{
//            System.out.println(p.toString());
//        });
        return productRepository.findAll();
    }
}
