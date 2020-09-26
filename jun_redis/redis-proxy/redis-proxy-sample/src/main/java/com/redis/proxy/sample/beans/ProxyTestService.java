/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redis.proxy.sample.beans;

import com.redis.proxy.client.factory.ProxyRdsConnFactory;
import com.redis.proxy.client.keys.DelayRdsBuild;
import com.redis.proxy.client.templates.ProxyRdsTemplate;
import com.redis.proxy.sample.vo.TestVo;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 *
 * @author zhanggaofeng
 */
@Service
public class ProxyTestService {

        private final DelayRdsBuild testKey = new DelayRdsBuild("module1", "test", 1, 1, TimeUnit.DAYS, TestVo.class);
        @Autowired
        private ProxyRdsConnFactory proxyRdsConnFactory;
        @Autowired
        private ProxyRdsTemplate proxyRdsTemplate;

        public ProxyTestService() {
                Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {

                        @Override
                        public void run() {
                                test1();
                                test2();
                        }
                }, 5, 5, TimeUnit.SECONDS);
        }

        /**
         * 使用自带模板方式
         */
        private void test1() {
                TestVo test = new TestVo("zhanggaofeng", (int) (Math.random() * 1000), "河南");
                proxyRdsTemplate.set(testKey.build("1"), test);
                TestVo value = proxyRdsTemplate.get(testKey.build("1"));
                System.err.println(value);
        }

        /**
         * 直接使用jedis方法
         */
        private void test2() {
                String redisKey = "module1_test1_1_1";
                Jedis jedis = proxyRdsConnFactory.getPool(redisKey);
                jedis.set(redisKey, "100");
                System.err.println(jedis.get(redisKey));
        }
}
