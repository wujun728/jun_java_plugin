package com.kancy.emailplus.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * LuaTest
 *
 * @author Wujun
 * @date 2020/3/6 10:34
 */
@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RedisAutoConfiguration.class})
public class LuaTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test01(){
        List<String> keys = new ArrayList<>();
        keys.add("polling-count");
        Object execute = stringRedisTemplate.execute(getRedisScript("lua/polling-count.lua", Long.class), keys, "3");
        System.out.println(execute);
    }

    private static DefaultRedisScript<String> getRedisScript(String path, Class returnClass) {
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(path)));
        defaultRedisScript.setResultType(returnClass);
        return defaultRedisScript;
    }


}
