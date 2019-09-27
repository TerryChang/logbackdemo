package com.terry.logbackdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.logbackdemo.vo.Product;
import com.terry.logbackdemo.vo.ShoppingCart;
import com.terry.logbackdemo.vo.ShoppingItem;
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

    private ShoppingCart shoppingCart;

    @Before
    public void setup() {
        Product product100 = new Product("100", 20);
        Product product101 = new Product("101", 1000);
        ShoppingItem shoppingItem100 = new ShoppingItem(product100, 3);
        ShoppingItem shoppingItem101 = new ShoppingItem(product101, 10);
        List<ShoppingItem> shoppingItemList = new ArrayList<>();
        shoppingItemList.add(shoppingItem100);
        shoppingItemList.add(shoppingItem101);
        shoppingCart = new ShoppingCart("terry", shoppingItemList);
    }

    @Test
    public void JSON_직렬화_객체를JSON_테스트() throws Exception {
        String jsonString = objectMapper.writeValueAsString(shoppingCart);
        logger.info(jsonString);
    }

    @Test
    public void JSON_역직렬화_JSON을객체로_테스트() throws Exception {
        String jsonString = "{\"userId\":\"terry\",\"shoppingItemList\":[{\"product\":{\"productId\":\"100\",\"productPrice\":20},\"cnt\":3},{\"product\":{\"productId\":\"101\",\"productPrice\":1000},\"cnt\":10}],\"totalPrice\":10060}";
        ShoppingCart myShoppingCart = objectMapper.readValue(jsonString, ShoppingCart.class);
        // assertThat("foo", equalTo("foo"));
        assertThat(myShoppingCart).isEqualToComparingFieldByFieldRecursively(shoppingCart);
    }
}
