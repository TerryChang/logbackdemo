package com.terry.logbackdemo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShoppingCartVO {
    private String loginId;

    @JsonProperty("shoppingItemList")
    List<ShoppingItemVO> shoppingItemVOList = new ArrayList<>();
    private long totalPrice;

    public ShoppingCartVO() {

    }

    public ShoppingCartVO(String loginId, List<ShoppingItemVO> shoppingItemVOList) {
        this.loginId = loginId;
        this.shoppingItemVOList = shoppingItemVOList;
        calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        totalPrice = 0L;
        shoppingItemVOList.forEach(
                shoppingItemVO -> {
                    totalPrice += shoppingItemVO.getTotalPriceByProduct();
                }
        );
    }
}
