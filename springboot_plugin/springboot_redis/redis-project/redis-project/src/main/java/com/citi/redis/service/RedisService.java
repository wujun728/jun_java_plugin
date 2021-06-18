package com.citi.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class RedisService {
    public static final int ONE_WEEK_IN_SECONDS= 7*86400;
    public static final int VOTE_SCORE=432;

    @Autowired
    StringRedisTemplate redisTemplate;

    public void article_vote(){

    }

    public String initData(){
        Map<String,Object> map1=new HashMap<>();
        map1.put("","");

        Map<String,Object> map=new HashMap<>();
        map.put("aaa",map1);
        map.put("title","Go to Statement considered harmful");
        map.put("link","http://goo.gl/kZUSu");
        map.put("poster","user:83271");
        map.put("time","1331382699.33");
        map.put("votes","528");
        Object obj=redisTemplate.opsForHash().get("","");
        redisTemplate.opsForHash().put("aaa",map,obj);
        redisTemplate.opsForHash().putAll("article:92617",map);
        redisTemplate.opsForZSet().add("time:","article:100408",1332065417.47);
        redisTemplate.opsForZSet().add("time:","article:100635",1332075503.49);
        redisTemplate.opsForZSet().add("time:","article:100716",1332082035.26);

        redisTemplate.opsForZSet().add("score:","article:100635",1332164063.49);
        redisTemplate.opsForZSet().add("score:","article:100408",1332174713.47);
        redisTemplate.opsForZSet().add("score:","article:100716",1332225027.26);
        return "successful";
    }

    public Set initZset(){
        for (int i=0;i<100;i++){
            redisTemplate.opsForZSet().add("zset-key","member"+i,i);
        }
        Set set= redisTemplate.opsForZSet().range("zset-key",0,-1);
        Set set1=redisTemplate.opsForZSet().rangeByScore("zset-key",1,4);
        System.out.println(set);
        System.out.println(set1);
        return set;
    }

    public void testTransaction(){
        redisTemplate.multi();
        redisTemplate.opsForList().leftPush("key","1");
        redisTemplate.opsForList().leftPush("key","2");
        redisTemplate.opsForList().leftPush("key","3");
        redisTemplate.exec();
    }
}
