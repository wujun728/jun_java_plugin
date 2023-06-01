package com.yangzheng.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangzheng.entity.TestEncrypt;
import com.yangzheng.mapper.TestEncryptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangzheng
 * @description
 * @date 2022/5/30 03016:07
 */
@RestController
@RequestMapping
@Slf4j
public class JdbcTestController {

    @Autowired
    private TestEncryptMapper testEncryptMapper;

    @RequestMapping("/jdbcTestSelect")
    public String jdbcTestSelect(@RequestBody JSONObject json) {
        TestEncrypt testEncrypt = testEncryptMapper.getTestEncryptById((Integer) json.get("id"));
        log.info("testEncrypt={}", JSON.toJSONString(testEncrypt, false));
        return "成功";
    }

    @RequestMapping("/jdbcTestInsert")
    public String jdbcTestInsert() {
        TestEncrypt t = new TestEncrypt();
        t.setName("test1");
        testEncryptMapper.insert(t);
        log.info("testEncrypt={}", JSON.toJSONString(t, false));
        return "成功";
    }
}
