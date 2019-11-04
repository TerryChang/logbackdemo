package com.terry.logbackdemo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProductVO {
    private Long idx;

    // 상품 이름은 로그에서 출력하지 않는 관계로 비교할때 상품 이름은 제외시키고 비교하기 위해 Exclude를 걸어주었다
    @EqualsAndHashCode.Exclude
    private String productName;

    private Integer productPrice;

    @Builder
    public ProductVO(Long idx, String productName, Integer productPrice) {
        this.idx = idx;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    /**
     * 상품 이름은 json 문자열로 표현하지 않기 때문에 getter 메소드에 @JsonIgnore 어노테이션을 붙였다
     * @return
     */
    @JsonIgnore
    public String getProductName() {
        return productName;
    }
}
