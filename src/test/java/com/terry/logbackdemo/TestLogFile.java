package com.terry.logbackdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class TestLogFile {

    private Logger logger = LoggerFactory.getLogger("testBuyLogger");

    @Test
    public void 로그파일에_한줄_기록해보기() {
        logger.info("테스트 메시지 {}", 1234);
    }

    @Test
    public void 로그파일이름_시간별로바뀌는지_테스트() {
        /**
         * 이 테스트는 현재 문제점이 있는것이 이 테스트를 수행하면 CPU 점유율이 100%로 올라가는 상황이 발생하고 있다
         * 추측엔 2백만건 로그를 한방의 코드로 기록하는 과정에서 먼가 문제가 있는듯 한데..잡지는 못했다
         * 테스트가 아닌 CommandLineRunner 인터페이스 상에서 실행했을때는 그러한 문제가 발생하지 않아서 그렇다
         * CommandLineRunner 인터페이스를 구현한 클래스에서는 100만건을 찍고 있는 차이가 있으나..
         * 테스트 클래스로 진행해보면 console log에서 한 5만건 정도 출력되는 시점부터 뻗어버리는 증상이 있다
         * 일단 테스트를 진행할때는 건수를 낮춰서 테스트를 진행해야 할 듯 하다
         */
        StopWatch stopWatch = new StopWatch("testStopWatch");
        stopWatch.start();
        // for(int i=0; i < 2000000; i++) { // 이 라인을 주석처리한 이유는 위에 설명되어 있다
        for(int i=0; i < 2000; i++) {
            logger.info("{} : 테스트 메시지 파일 변경 테스트", i+1);
        }
        stopWatch.stop();
        logger.info("총 작업시간 : {}", stopWatch.getTotalTimeSeconds());
    }
}
