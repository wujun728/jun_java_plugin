package com.zh.springbootcommand.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Wujun
 * @date 2019/5/31
 */
@Slf4j
@Order(1)
@Component
public class FirstLoadResourseCommangRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("===================第1个资源加载啦======================");
    }
}
