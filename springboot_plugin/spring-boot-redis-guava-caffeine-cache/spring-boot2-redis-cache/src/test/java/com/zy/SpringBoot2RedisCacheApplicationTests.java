package com.zy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot2RedisCacheApplicationTests {

    @Resource(name = "redisTemplateB")
    private RedisTemplate redisTemplateB;

    @Resource(name = "redisTemplateA")
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForValue().set("redisTemplateA", "A");
        redisTemplateB.opsForValue().set("redisTemplateB", "B");
    }

}
