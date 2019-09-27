package com.terry.logbackdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShoppingItem {
    private Product product;
    private int cnt;

    public ShoppingItem() {

    }
}
