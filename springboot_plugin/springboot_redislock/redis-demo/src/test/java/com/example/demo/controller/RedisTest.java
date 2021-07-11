package com.example.demo.controller;

import com.example.demo.DemoApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() throws Exception {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    //测试事务
    @Test
    public void testRedisTransaction(){
        System.out.println("事务开始，直到事务结束前都输出null");
        Object o = stringRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //   operations.watch("testRedisMulti");
                operations.multi();
                operations.opsForValue().set("testRedisMulti", "0");
                String now = (String) operations.opsForValue().get("testRedisMulti");
                System.out.println(now);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                now = (String) operations.opsForValue().get("testRedisMulti");
                System.out.println(now);
                Object rs = operations.exec();
                return "事务执行结果："+rs.toString();
            }
        });
        System.out.println("事务结束，直到事务结束前都输出null");
        System.out.println(o);
    }

    @Test
    public void testJRedis() {
//        Jedis jedis = null;
//        while (true) {
//            System.out.println(Thread.currentThread().getName());
//            jedis = new Jedis();
//            try {
//                int stock = Integer.parseInt(jedis.get("mykey"));
//                if (stock > 0) {
//                    jedis.watch("mykey");
//                    Transaction transaction = jedis.multi();
//                    transaction.set("mykey", String.valueOf(stock - 1));
//                    List<Object> result = transaction.exec();
//                    if (result == null || result.isEmpty()) {
//                        System.out.println("Transaction error...");// 可能是watch-key被外部修改，或者是数据操作被驳回
//                    }
//                } else {
//                    System.out.println("库存为0");
//                    break;
//                }
//            } catch (Exception e) {
//
//            }
//        }
        Jedis jedis = new Jedis();
        jedis.set("test","test");
    }

}