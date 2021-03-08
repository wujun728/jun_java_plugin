package com.zh.springbootscheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Slf4j
@Component
public class HelloJob {

    @Scheduled(fixedRate = 5000)
    public void printHello(){
        log.info("====================Hello World 2019=====================");
    }

    @Scheduled(cron = "0 05 16 * * ?")
    public void printNow(){
        log.info("====================北京时间:{}=====================", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }
}
