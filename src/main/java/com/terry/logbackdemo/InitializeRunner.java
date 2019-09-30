package com.terry.logbackdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.logbackdemo.vo.Product;
import com.terry.logbackdemo.vo.ShoppingCart;
import com.terry.logbackdemo.vo.ShoppingItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class InitializeRunner implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger("testBuyLogger");

    @Override
    public void run(String... args) throws Exception {
        String [] loginIds = {"microsoft.com", "oracle.com", "adobe.com", "pivotal.com", "jetbrains.com"};
        String [] productIds = {"1", "2", "3", "4", "5", "6"};
        int [] prices = {500, 700, 1000, 1200, 900, 1500};

        Random random = new Random();

        List<Product> productList = new ArrayList<>();
        for(int i=0; i < productIds.length; i++) {
            Product product = new Product(productIds[i], prices[i]);
            productList.add(product);
        }

        for(int i=0; i < 1000000; i++) {

            // 사용자 아이디 random 추출
            String userId = loginIds[random.nextInt(loginIds.length)];

            // 상품을 몇가지를 사는지를 random 추출
            int shoppingItemCount = random.nextInt(5) + 1;
            List<ShoppingItem> shoppingItemList = new ArrayList<>();

            for(int j = 1; j <= shoppingItemCount; j++) {
                // 상품 리스트에서 특정 상품 random //ㅏ
                Product product = productList.get(random.nextInt(productList.size()));
                // 상품갯수 random 추출
                int productCnt = random.nextInt(10);

                ShoppingItem shoppingItem = new ShoppingItem(product, productCnt);
                shoppingItemList.add(shoppingItem);
            }

            ShoppingCart shoppingCart = new ShoppingCart(userId, shoppingItemList);

            String jsonString = objectMapper.writeValueAsString(shoppingCart);

            logger.info(jsonString);

        }
    }
}
