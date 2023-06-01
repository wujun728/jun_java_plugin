package com.yangzheng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangzheng.entity.TestEncrypt;
import com.yangzheng.mapper.TestEncryptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author yangzheng
 * @description
 * @date 2022/5/30 03016:07
 */
@RestController
@RequestMapping
@Slf4j
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/redisTest")
    public String jdbcTestSelect() {
        redisTemplate.opsForValue().set("num","123");
        String num = (String) redisTemplate.opsForValue().get("num");
        log.info("num={}",num);
        return "成功";
    }


}
