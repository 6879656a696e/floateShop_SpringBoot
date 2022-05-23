package com.woori.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long product_key;

    @Column(nullable=false)
    private String product_name;

    @Column(nullable = false)
    private String product_num;

    @Column(nullable=false)
    private String product_category;

    @Column
    private String product_pic;

    @Column(nullable = false)
    private int product_price;

    @Column(columnDefinition = "LONGTEXT")
    private String product_desc;

    @Column(nullable=false)
    private int product_cnt;

    @Column
    private boolean activated;
}
