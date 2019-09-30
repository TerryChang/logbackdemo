package com.terry.logbackdemo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ShoppingItem {
    private Product product;
    private int cnt;
    private int totalPriceByProduct;

    public ShoppingItem() {

    }

    public ShoppingItem(Product product, int cnt) {
        this.product = product;
        this.cnt = cnt;
        this.totalPriceByProduct = product.getProductPrice() * cnt;
    }
}
