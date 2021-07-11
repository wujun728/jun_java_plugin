package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于测试redis秒杀
 */
@RestController
@RequestMapping("/api/spike")
@Slf4j
public class SpikeController {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private AtomicInteger successNum = new AtomicInteger(0);

    @RequestMapping(value = "/initSku", method = RequestMethod.GET)
    public String initSku() {
        stringRedisTemplate.opsForValue().set("product_sku", "5");
        successNum.set(0);
        return "初始化库存成功";
    }

    /**
     * 会出现超卖情况的减少库存方式
     *
     * @return
     */
    @RequestMapping(value = "/reduceSku", method = RequestMethod.GET)
    public String reduceSku() {
        Integer sku = Integer.parseInt(stringRedisTemplate.opsForValue().get("product_sku"));
        sku = sku - 1;
        if (sku < 0) {
            return "库存不足";
        }

        stringRedisTemplate.opsForValue().set("product_sku", sku.toString());

        return "减少库存成功,共减少" + successNum.incrementAndGet();
    }


    /**
     * 加入事务的减少库存方式
     *
     * @return
     */
    @RequestMapping(value = "/reduceSku2", method = RequestMethod.GET)
    public String reduceSku2() {
        stringRedisTemplate.setEnableTransactionSupport(true);
        List<Object> results = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.watch("product_sku");
                String product_sku = (String) operations.opsForValue().get("product_sku");
                operations.multi();
                operations.opsForValue().get("product_sku");//必要的空查询
                Integer sku = Integer.parseInt(product_sku);
                sku = sku - 1;
                if (sku < 0) {
                    return null;
                }
                operations.opsForValue().set("product_sku", sku.toString());
                return operations.exec();
//                operations.unwatch(); //执行exec()后自动unwatch()

            }
        });

        if (results != null && results.size() > 0) {
            return "减少库存成功,共减少" + successNum.incrementAndGet();
        }

        return "库存不足";
//        return result.toString();
    }


    /**
     * 直接用jredis加入事务的减少库存方式
     *
     * @return
     */
    @RequestMapping(value = "/reduceSku3", method = RequestMethod.GET)
    public String reduceSku3() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        List<Object> result ;
        Transaction transaction = null;
        try {
            jedis.watch("product_sku");
            int sku = Integer.parseInt(jedis.get("product_sku"));
            if (sku > 0) {
                transaction = jedis.multi();
                transaction.set("product_sku", String.valueOf(sku - 1));
//                int exp = 1/0;
                result = transaction.exec();
                if (result == null || result.isEmpty()) {
                    System.out.println("Transaction error...");// 可能是watch-key被外部修改，或者是数据操作被驳回
//                    transaction.discard();  //watch-key被外部修改时，discard操作会被自动触发
                    return "Transaction error...";
                }
            } else {
                return "库存不足";
            }
            return "减少库存成功,共减少" + successNum.incrementAndGet();
        } catch (Exception e) {
            log.error(e.getMessage());
            transaction.discard();
            return "fail";
        }
    }

    @RequestMapping(value = "/reduceSku4", method = RequestMethod.GET)
    public String reduceSku4() {
        RLock rLock = redissonClient.getLock("product_sku");
        try {
            rLock.lock();

            Integer sku = Integer.parseInt(stringRedisTemplate.opsForValue().get("product_sku"));
            sku = sku - 1;
            if (sku < 0) {
                return "库存不足";
            }

            stringRedisTemplate.opsForValue().set("product_sku", sku.toString());

            return "减少库存成功,共减少" + successNum.incrementAndGet();
        } finally {
            rLock.unlock();
        }

    }

    @RequestMapping(value = "/successNum", method = RequestMethod.GET)
    public String successNum() {
        return "顾客成功抢到的商品数量：" + successNum.get();
    }
}
