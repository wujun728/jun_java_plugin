package com.lxx.springboot;

import com.lxx.springboot.dao.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Autowired
    RedisDao redisDao;

    @Test
    public void testRedis(){
        redisDao.setKey("name", "luo");
        redisDao.setKey("age", "26");
        log.info("日志输出：[name--] "+ redisDao.getValue("name"));
        log.info("日志输出: [age--] "+ redisDao.getValue("age"));
    }
}
