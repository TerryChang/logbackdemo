package com.terry.logbackdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.logbackdemo.entity.Product;
import com.terry.logbackdemo.vo.ProductVO;
import com.terry.logbackdemo.vo.ShoppingCartVO;
import com.terry.logbackdemo.vo.ShoppingItemVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class JsonTest {

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCartVO shoppingCartVO;

    @Before
    public void setup() {
        ProductVO productVO100 = ProductVO.builder().idx(100L).productPrice(20).build();
        ProductVO productVO101 = ProductVO.builder().idx(101L).productPrice(1000).build();
        ShoppingItemVO shoppingItemVO100 = new ShoppingItemVO(productVO100, 3);
        ShoppingItemVO shoppingItemVO101 = new ShoppingItemVO(productVO101, 10);
        List<ShoppingItemVO> shoppingItemVOList = new ArrayList<>();
        shoppingItemVOList.add(shoppingItemVO100);
        shoppingItemVOList.add(shoppingItemVO101);
        shoppingCartVO = new ShoppingCartVO("terry", shoppingItemVOList);
    }

    @Test
    public void JSON_직렬화_객체를JSON_테스트() throws Exception {
        String jsonString = objectMapper.writeValueAsString(shoppingCartVO);
        logger.info(jsonString);
    }

    @Test
    public void JSON_역직렬화_JSON을객체로_테스트() throws Exception {
        String jsonString = "{\"loginId\":\"terry\",\"shoppingItemList\":[{\"product\":{\"idx\":100,\"productPrice\":20},\"cnt\":3},{\"product\":{\"idx\":101,\"productPrice\":1000},\"cnt\":10}],\"totalPrice\":10060}";
        ShoppingCartVO myShoppingCartVO = objectMapper.readValue(jsonString, ShoppingCartVO.class);
        // assertThat("foo", equalTo("foo"));
        assertThat(myShoppingCartVO).isEqualToComparingFieldByFieldRecursively(shoppingCartVO);
    }
}
