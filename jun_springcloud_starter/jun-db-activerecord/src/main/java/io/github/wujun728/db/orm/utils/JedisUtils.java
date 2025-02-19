package io.github.wujun728.db.orm.utils;

import io.github.wujun728.db.orm.core.Register;
import redis.clients.jedis.Jedis;

/**
 * Jedis工具类
 */
public class JedisUtils {

    public static Jedis getJedis() {
        return Register.jedisPool.getResource();
    }

    public static void set(String key, String value) {
        try (Jedis jedis = getJedis()) {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean exists(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
