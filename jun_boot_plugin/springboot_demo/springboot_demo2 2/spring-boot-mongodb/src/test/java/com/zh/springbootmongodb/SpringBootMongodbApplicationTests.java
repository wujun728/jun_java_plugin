package com.zh.springbootmongodb;

import com.zh.springbootmongodb.entity.model.AppVisitLog;
import com.zh.springbootmongodb.service.AppVisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMongodbApplicationTests {

    @Autowired
    private AppVisitLogService appVisitLogService;

    @Test
    public void contextLoads() {
        AppVisitLog appVisitLog = this.appVisitLogService.findByUuidAndCreateTime("d5aeb799f332492d9aafc2d4782e7883",new Date());
        log.info("appVisitLog:{}",appVisitLog);
    }

}
