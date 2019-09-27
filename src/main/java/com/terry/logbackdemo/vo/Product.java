package com.terry.logbackdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Product {
    private String productId;
    private int productPrice;

    public Product() {

    }
}
