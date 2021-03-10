package com.kind.redis.client;

import com.kind.redis.client.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-redis-test.xml"})
public class RedisClientTest extends JUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(RedisClientTest.class);
    @Resource
    private RedisClient redisClient;

    @Before
    public void before() throws Exception {

    }

    @Test
    public void test() throws InterruptedException {
        System.out.println("redisClient:" + redisClient);
        // 清空所有
        // redisClient.clearAll();
        System.out.println(redisClient.get("123"));
        // 普通字符串
        redisClient.set("123", "redis1");
        Object o = redisClient.get("123");
        System.out.println(o);

        // 对象
        User user = new User();
        user.setId("111");
        user.setName("测试");
        redisClient.set("u", user);
        user = (User) redisClient.get("u");
        logger.debug(user.toString());

        // List字符串
        List<String> list = new ArrayList<>();
        list.add("6");
        list.add("7");
        list.add("8");
        Long l = redisClient.lPush("list", list.toArray());
        System.out.println(redisClient.lrange("list", 0, l));
        List<String> list2 = new ArrayList<>();
        list2.add("55");
        Long l2 = redisClient.lPush("list", list2.toArray());
        System.out.println(redisClient.lrange("list", 0, l2));

        // List对象
        User u1 = new User();
        u1.setId("111");
        u1.setName("测试");
        User u2 = new User();
        u2.setId("111");
        u2.setName("测试");
        User u3 = new User();
        u3.setId("111");
        u3.setName("测试");
        List<User> li = new ArrayList<>();
        li.add(u1);
        li.add(u2);
        li.add(u3);
        System.out.println(li);
        Long ul = redisClient.lPush("ulist", li.toArray());
        System.out.println(redisClient.lrange("ulist", 0, ul));

        Map<String, Object> map = new HashMap<>();
        map.put("123", "你好啊");
        redisClient.hset("map", map);

        boolean f = redisClient.hisExists("map", "321");
        System.out.println(f);
    }

}
