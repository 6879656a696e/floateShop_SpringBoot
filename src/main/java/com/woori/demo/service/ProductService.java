package com.woori.demo.service;

import com.woori.demo.domain.Authority;
import com.woori.demo.domain.Product;
import com.woori.demo.domain.User;
import com.woori.demo.dto.ProductDto;
import com.woori.demo.dto.UserDto;
import com.woori.demo.exception.ResourceNotFoundException;
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
                .productName(dto.getProductName())
                .productCategory(dto.getProductCategory())
                .productCnt(dto.getProductCnt())
                .productDesc(dto.getProductDesc())
                .productPic(dto.getProductPic())
                .fileId(dto.getFileId())
                .productPrice(dto.getProductPrice())
                .productNum(dto.getProductNum())
                .activated(true)
                .build();

        return productRepository.save(product);
    }

    public List<Product> getProductList(){
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> getProductDetail(Long id){
        return productRepository.findByProductKey(id);
    }

    public Product findProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productKey", id));
        return product;
    }
}
