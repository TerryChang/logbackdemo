package com.terry.logbackdemo.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ShoppingItemVO {

    @JsonProperty("product")
    private ProductVO productVO;
    private int cnt;
    private int totalPriceByProduct;

    public ShoppingItemVO() {
        if(1 == 1) {

        }
    }

    // JSON을 Java 객체로 변환할때 이 생성자를 사용하게끔 유도하기 위해 @JsonConstructor 어노테이션을 붙였다
    @JsonCreator
    public ShoppingItemVO(ProductVO productVO, int cnt) {
        this.productVO = productVO;
        this.cnt = cnt;
        this.totalPriceByProduct = productVO.getProductPrice() * cnt;
    }
}
