package com.zh.java8stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OptionalTest {

    private String str = null;
    private String str2 = "A";
    private String str3 = "B";

    public static String get(){
        log.info("=======================老铁执行啦666=============================");
        return "ABC";
    }

    /**
     * orElse
     * 前面没值则返回新值
     * 前面有值则返回前面值
     */
    @Test
    public void orElseTest() {
        String result = Optional.ofNullable(str).orElse("str参数为空!");
        log.info("=================={}==================",result);
        result = Optional.ofNullable(str2).orElse("str参数为空!");
        log.info("=================={}==================",result);
        result = Optional.ofNullable(str2).filter(e -> e.equals("B")).orElse("非法参数");
        log.info("=================={}==================",result);
        result = Optional.ofNullable(str3).filter(e -> e.equals("B")).map(e -> e.toLowerCase()).orElse("非法参数");
        log.info("=================={}==================",result);
    }

    /**
     * orElseGet
     * 前面没值则执行
     * 前面有值则不执行
     */
    @Test
    public void orElseGetTest() {
        String result = Optional.ofNullable(str).orElseGet(() -> get());
        log.info("=================={}==================",result);
        result = Optional.ofNullable(str2).orElseGet(() -> get());
        log.info("=================={}==================",result);
    }

    /**
     * orElseThrow
     * 前面没值则抛异常
     * 前面有值则不执行
     */
    @Test
    public void orElseThrowTest() {
        String result = Optional.ofNullable(str2).filter(e -> e.equals("B")).orElseThrow(() -> new RuntimeException("非法参数"));
        log.info("=================={}==================",result);
    }

}
