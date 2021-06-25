import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/7.
 */
public class RedisJava1 {

    public static void main(String... args) {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(10000);
//        config.setMaxIdle(10);
//        config.setMaxWaitMillis(60 * 60 * 1000);
//        config.setTestWhileIdle(true);
//        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, 60 * 1000);
//        Jedis jedis = jedisPool.getResource();

        HostAndPort host1 = new HostAndPort("127.0.0.1", 6379);
        HostAndPort host2 = new HostAndPort("127.0.0.1", 6389);
        Set<HostAndPort> hosts = new HashSet<HostAndPort>();
        hosts.add(host1);
        hosts.add(host2);
        JedisCluster cluster = new JedisCluster(hosts, 60 * 1000);
        cluster.set("key1", "value1");
        System.out.println(cluster.get("key1"));
    }
}
