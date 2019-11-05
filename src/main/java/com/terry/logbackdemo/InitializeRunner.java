package com.terry.logbackdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.logbackdemo.entity.Product;
import com.terry.logbackdemo.entity.User;
import com.terry.logbackdemo.repository.ProductRepository;
import com.terry.logbackdemo.repository.UserRepository;
import com.terry.logbackdemo.vo.ProductVO;
import com.terry.logbackdemo.vo.ShoppingCartVO;
import com.terry.logbackdemo.vo.ShoppingItemVO;
import com.terry.logbackdemo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class InitializeRunner implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger("testBuyLogger");
    private final int TOTAL_DATA_CNT = 15;                  // 만들어야 할 총 데이터건수

    @Override
    public void run(String... args) throws Exception {

        // localDataVersion();
        jpaversion();
    }

    /**
     * JPA를 사용해서 로그를 생성하는 함수
     * @throws Exception
     */
    private void jpaversion() throws Exception {
        List<User> userList = userRepository.findAllByOrderByIdxAsc();
        List<Product> productList = productRepository.findAllByOrderByIdxAsc();

        Random random = new Random();

        List<UserVO> userVOList = userList.stream().map(user -> UserVO.builder()
                                                                    .idx(user.getIdx())
                                                                    .name(user.getName())
                                                                    .loginId(user.getLoginId())
                                                                    .build())
                                                    .collect(Collectors.toList());
        List<ProductVO> productVOList = productList.stream().map(product -> ProductVO.builder()
                                                                                    .idx(product.getIdx())
                                                                                    .productName(product.getProductName())
                                                                                    .productPrice(product.getProductPrice())
                                                                                    .build())
                                                            .collect(Collectors.toList());

        int userVOListSize = userVOList.size();
        int productVOListSize = productVOList.size();

        for(int i=0; i < TOTAL_DATA_CNT; i++) {
            // 사용자 리스트를 조회해서 이를 랜덤하게 뽑음
            UserVO userVO = userVOList.get(random.nextInt(userVOListSize));
            // 몇가지 종류의 상품을 살것인지 그 수치를 랜덤한 수치로 뽑는다(1~5 사이로)
            int shoppingItemCount = random.nextInt(5) + 1;
            List<ShoppingItemVO> shoppingItemVOList = new ArrayList<>();

            for (int j = 1; j <= shoppingItemCount; j++) {
                // 임의의 상품을 하나 뽑는다
                ProductVO productVO = productVOList.get(random.nextInt(productVOListSize));
                // 상품을 몇개를 살 것인지 그 수치를 1~10 사이의 숫자로 뽑는다
                int productCnt = random.nextInt(10) + 1;
                ShoppingItemVO shoppingItemVO = new ShoppingItemVO(productVO, productCnt);
                shoppingItemVOList.add(shoppingItemVO);
            }

            ShoppingCartVO shoppingCartVO = new ShoppingCartVO(userVO.getLoginId(), shoppingItemVOList);

            String jsonString = objectMapper.writeValueAsString(shoppingCartVO);

            logger.info(jsonString);
        }
    }


    /**
     * DB를 이용하지 않고 가상의 데이터를 만들어서 로그를 구현하는 메소드
     * @throws Exception
     */
    private void localDataVersion() throws Exception {
        String [] loginIds = {"microsoft.com", "oracle.com", "adobe.com", "pivotal.com", "jetbrains.com"};
        Long [] productIds = {1L, 2L, 3L, 4L, 5L, 6L};
        int [] prices = {500, 700, 1000, 1200, 900, 1500};

        Random random = new Random();

        List<ProductVO> productVOList = new ArrayList<>();
        for(int i=0; i < productIds.length; i++) {
            ProductVO productVO = ProductVO.builder().idx(productIds[i]).productPrice(prices[i]).build();
            productVOList.add(productVO);
        }

        for(int i=0; i < TOTAL_DATA_CNT; i++) {

            // 사용자 아이디 random 추출
            String userId = loginIds[random.nextInt(loginIds.length)];

            // 상품을 몇가지를 사는지를 random 추출
            int shoppingItemCount = random.nextInt(5) + 1;
            List<ShoppingItemVO> shoppingItemVOList = new ArrayList<>();

            for (int j = 1; j <= shoppingItemCount; j++) {
                // 상품 리스트에서 특정 상품 random //ㅏ
                ProductVO productVO = productVOList.get(random.nextInt(productVOList.size()));
                // 상품갯수 random 추출
                int productCnt = random.nextInt(10);

                ShoppingItemVO shoppingItemVO = new ShoppingItemVO(productVO, productCnt);
                shoppingItemVOList.add(shoppingItemVO);
            }

            ShoppingCartVO shoppingCartVO = new ShoppingCartVO(userId, shoppingItemVOList);

            String jsonString = objectMapper.writeValueAsString(shoppingCartVO);

            logger.info(jsonString);
        }
    }
}
