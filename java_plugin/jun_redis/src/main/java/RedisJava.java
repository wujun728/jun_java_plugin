import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/7.
 */
public class RedisJava {
    public static void main(String[] args) throws InterruptedException {
        // 连接Redis数据库
        Jedis jedis = new Jedis("127.0.0.1", 6379, 60000);
        System.out.println("连接成功");
        System.out.println("服务正在运行: " + jedis.ping());
        jedis.set("string_key", "你好！");
        System.out.println("string……");
        System.out.println(jedis.get("string_key"));
        if (jedis.exists("list_key")) {
            jedis.del("list_key");
        }
        jedis.lpush("list_key", "list1", "list2", "list3");
        List<String> list = jedis.lrange("list_key", 0, 20);
        System.out.println("list……");
        for (String s : list) {
            System.out.println(s);
        }
        jedis.sadd("set_key", "set1", "set2", "set3");
        Set<String> set = jedis.smembers("set_key");
        System.out.println("set……");
        for (String s : set) {
            System.out.println(s);
        }
        jedis.zadd("zset_key", 1, "zset1");
        jedis.zadd("zset_key", 2, "zset2");
        jedis.zadd("zset_key", 0, "zset3");
        Set<String> zset = jedis.zrange("zset_key", 0, 10);
        System.out.println("zset……");
        for (String s : zset) {
            System.out.println(s);
        }
        jedis.hset("hash_key", "field1", "value1");
        jedis.hset("hash_key", "field2", "value2");
        System.out.println(jedis.hget("hash_key", "field2"));
        System.out.println("hash……");
        List<String> hashValues = jedis.hmget("hash_key", "field1", "field2");
        for (String s : hashValues) {
            System.out.println(s);
        }

        jedis.set("key1", "value1");
        jedis.expire("key1", 5);
        Thread.sleep(6 * 1000);
        if (!jedis.exists("key1")) {
            System.out.println("已失效");
        }
    }
}