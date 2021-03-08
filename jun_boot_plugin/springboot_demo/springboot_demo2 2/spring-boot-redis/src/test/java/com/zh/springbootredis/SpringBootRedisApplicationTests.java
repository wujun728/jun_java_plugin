package com.zh.springbootredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.ReactiveGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringBootRedisApplicationTests {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * string:字符串
     * 最常用的就是incr操作，比如可以用来维护用户在某个抽奖活动的剩余抽奖次数
     * setnx方法可以用来实现分布式锁
     */
    @Test
    public void stringTest() {
        this.stringRedisTemplate.opsForValue().set("string","Hello World 2019");
        String value = this.stringRedisTemplate.opsForValue().get("java");
        log.info("============================java:{}============================",value);
    }

    /**
     * list:有序可重复集合
     * 可以作为简单的消息队列，通过list的lpush以及brpop作为消息队列的入队及消费的操作
     */
    @Test
    public void listTest() {
        this.redisTemplate.opsForList().rightPush("list-user",new User(1,"张三"));
        this.redisTemplate.opsForList().rightPush("list-user",new User(2,"李四"));
        List<Object> listUser = this.redisTemplate.opsForList().range("list-user",0,-1);
        log.info("============================list-user:{}===============================",listUser.toString());
        List<User> list = Arrays.asList(new User(3,"王五"),new User(4,"赵六"));
        this.redisTemplate.opsForList().rightPushAll("list-user-list",list);
        List<Object> listUserList = this.redisTemplate.opsForList().range("list-user-list",0,-1);
        log.info("============================list-user-list:{}===============================",listUserList.toString());
    }

    /**
     * set:无序不可重复集合
     * 可以用来存储每个标签对应的文章id
     * 也可以用来存储每个文章的已投票用户id，通过add返回值可以判断该值之前是否已经存在
     */
    @Test
    public void setTest() {
        List<User> list1 = Arrays.asList(new User(5,"田七"),new User(6,"陈八"));
        this.redisTemplate.opsForSet().add("set-user1",list1.toArray());
        Set<User> result = this.redisTemplate.opsForSet().members("set-user1");
        log.info("============================set-user1:{}===============================",result.toString());
        List<User> list2 = Arrays.asList(new User(6,"陈八"),new User(7,"钱九"));
        this.redisTemplate.opsForSet().add("set-user2",list2.toArray());
        result = this.redisTemplate.opsForSet().members("set-user2");
        log.info("============================set-user2:{}===============================",result.toString());
        result = this.redisTemplate.opsForSet().intersect("set-user1","set-user2");
        log.info("============================set-user-交集:{}===============================",result.toString());
        result = this.redisTemplate.opsForSet().union("set-user1","set-user2");
        log.info("============================set-user-并集:{}===============================",result.toString());
        result = this.redisTemplate.opsForSet().difference("set-user2","set-user1");
        log.info("============================set-user-差集:{}===============================",result.toString());
    }

    /**
     * zset:有序不可重复集合
     * 可以用来存储文章的得票数，使用得票数作为score，使用zset排序得出投票最高的前N篇文章
     * 或者用来存储最近登录的用户id，使用时间作为score，使用zset排序得出最近登录的前N个用户id
     * 也可以存储用户最近浏览的物品，使用时间作为score，使用zset排序得出用户最近浏览的前N个物品
     * 也可以存储物品最近浏览的用户，使用时间作为score，使用zset排序得出最近浏览该物品的前N个用户
     */
    @Test
    public void zsetTest() {
        this.redisTemplate.opsForZSet().add("zset-articleStar",1,49);
        this.redisTemplate.opsForZSet().add("zset-articleStar",2,44);
        this.redisTemplate.opsForZSet().add("zset-articleStar",3,36);
        this.redisTemplate.opsForZSet().add("zset-articleStar",4,110);
        this.redisTemplate.opsForZSet().add("zset-articleStar",5,74);
        Set<Integer> sortedArticleIds = this.redisTemplate.opsForZSet().reverseRange("zset-articleStar",0,-1);
        log.info("=========================文章被赞从高到低是:{}=========================",sortedArticleIds.toString());
        sortedArticleIds = this.redisTemplate.opsForZSet().range("zset-articleStar",0,-1);
        log.info("=========================文章被赞从低到高是:{}=========================",sortedArticleIds.toString());
        Long index = this.redisTemplate.opsForZSet().reverseRank("zset-articleStar",4);
        log.info("=========================第4篇文章被赞数排第{}=========================",index);
        Double starCount = this.redisTemplate.opsForZSet().score("zset-articleStar",1);
        log.info("=========================第1篇文章被赞{}次=========================",starCount);
        starCount = this.redisTemplate.opsForZSet().incrementScore("zset-articleStar",1,1);
        log.info("=========================给第1篇文章再赞1次,现在有{}个赞=========================",starCount);
        sortedArticleIds = this.redisTemplate.opsForZSet().rangeByScore("zset-articleStar",0,50);
        log.info("=========================50个赞以下的文章是:{}=========================",sortedArticleIds.toString());

    }

    /**
     * hash对象
     * 可以用来存储session，作为分布式session的一个实现方案
     * 可以用来存储用户购物车，value值存储的key为物品，value为其数量
     */
    @Test
    public void hashTest() {
        this.redisTemplate.opsForHash().put("hash-user","移动",new User(10086,"移动"));
        this.redisTemplate.opsForHash().put("hash-user","电信",new User(10000,"电信"));
        User user = (User) this.redisTemplate.opsForHash().get("hash-user","移动");
        log.info("==============================hash-user姓名:{}===============================",user.getName());
        this.redisTemplate.opsForHash().delete("hash-user","电信");
    }

    /**
     * geo:地理位置
     * 底层是zset
     * 使用geo来存储poi信息，比如存储门店的经纬度，之后可以根据半径查询附件的门店信息
     */
    @Test
    public void geoTest(){
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("南京",new Point(118.78,32.04)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("徐州",new Point(117.2,34.26)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("扬州",new Point(119.42,32.39)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("镇江",new Point(119.44,32.2)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("泰州",new Point(119.9,32.49)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("常州",new Point(119.95,31.79)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("无锡",new Point(120.29,31.59)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("苏州",new Point(120.62,31.32)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("南通",new Point(120.86,32.01)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("宿迁",new Point(118.3,33.96)));
        this.redisTemplate.opsForGeo().add("geo-city",new RedisGeoCommands.GeoLocation("盐城",new Point(120.13,33.38)));
        List<String> hasList = this.redisTemplate.opsForGeo().hash("geo-city","徐州","北京");
        log.info("=========================geo-city:徐州的hash:{},北京的hash:{}========================",hasList.get(0),hasList.get(1));
        List<Point> list = this.redisTemplate.opsForGeo().position("geo-city","徐州");
        log.info("=========================geo-city:徐州经度:{},徐州纬度:{}============================",list.get(0).getX(),list.get(0).getY());
        Distance distanceM = this.redisTemplate.opsForGeo().distance("geo-city", "南京", "徐州");
        log.info("=========================geo-city:徐州距离南京:{}m===================================",distanceM.getValue());
        Distance distanceKm = this.redisTemplate.opsForGeo().distance("geo-city", "南京", "徐州", Metrics.KILOMETERS);
        log.info("=========================geo-city:徐州距离南京:{}km===================================",distanceKm.getValue());
        Circle circle = new Circle(new Point(118.78,32.04),new Distance(120,Metrics.KILOMETERS));
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                                                                     .newGeoRadiusArgs()
                                                                     .includeCoordinates()
                                                                     .includeDistance()
                                                                     .sortAscending()
                                                                     .limit(3);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = this.redisTemplate.opsForGeo().radius("geo-city",circle,args);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> geoResultList = results.getContent();
        geoResultList.forEach(e -> {
            double dist = e.getDistance().getValue();
            RedisGeoCommands.GeoLocation<String> location = e.getContent();
            String name = location.getName();
            double x = location.getPoint().getX();
            double y = location.getPoint().getY();
            log.info("======================={}的经度:{},纬度:{},距离南京{}km============================",name,x,y,dist);
        });
        Distance distance = new Distance(120,Metrics.KILOMETERS);
        results = this.redisTemplate.opsForGeo().radius("geo-city","南京",distance,args);
        geoResultList = results.getContent();
        geoResultList.forEach(e -> {
            double dist = e.getDistance().getValue();
            RedisGeoCommands.GeoLocation<String> location = e.getContent();
            String name = location.getName();
            double x = location.getPoint().getX();
            double y = location.getPoint().getY();
            log.info("======================={}的经度:{},纬度:{},距离南京{}km============================",name,x,y,dist);
        });
    }

    /**
     * hyperloglog:Probabilistic data Structures的一种
     * 底层是string
     * 这类数据结构的基本大的思路就是使用统计概率上的算法，牺牲数据的精准性来节省内存的占用空间及提升相关操作的性能
     * 用来粗略统计网站的每日UV
     * 别是适合用来对海量数据进行unique统计，对内存占用有要求，而且还能够接受一定的错误率的场景
     * 对于union操作由于是O(N)，在海量数据层面需要注意慢查询问题
     */
    @Test
    public void hyperLogLogTest(){
        String uv1 = "uv1";
        String uv2 = "uv2";
        IntStream.rangeClosed(1,100).forEach(e -> {
            this.redisTemplate.opsForHyperLogLog().add(uv1,"user_" + e);
            this.redisTemplate.opsForHyperLogLog().add(uv2,"user_" + e/2);
        });
        long uv1Count = this.redisTemplate.opsForHyperLogLog().size(uv1);
        log.info("=======================uv1的数量为：{}============================",uv1Count);
        long uv2Count = this.redisTemplate.opsForHyperLogLog().size(uv2);
        log.info("=======================uv2的数量为：{}============================",uv2Count);
        String uv3 = "uv3";
        this.redisTemplate.opsForHyperLogLog().union(uv3,uv1,uv2);
        long uv3Count = this.redisTemplate.opsForHyperLogLog().size(uv3);
        log.info("=======================uv3的数量为：{}============================",uv3Count);
    }
}
