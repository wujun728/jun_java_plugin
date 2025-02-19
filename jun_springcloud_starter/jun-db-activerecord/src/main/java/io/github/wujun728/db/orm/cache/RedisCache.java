package io.github.wujun728.db.orm.cache;

import com.alibaba.fastjson.JSON;
import io.github.wujun728.db.orm.core.Model;
import io.github.wujun728.db.orm.core.Register;
import io.github.wujun728.db.orm.utils.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Redis缓存
 */
public class RedisCache<T> {

    private final static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    /**
     * 获取缓存
     *
     * @param pk    主键
     * @param clazz model类型
     * @return Model
     */
    public T getCache(Object pk, Class<? extends Model> clazz) {
        String key = clazz.getSimpleName() + ":" + pk;
        try (Jedis jedis = JedisUtils.getJedis()) {
            if (null != jedis) {
                String json = jedis.get(key);
                return (T) JSON.parseObject(json, clazz);
            }
        }
        return null;
    }

    /**
     * 修改缓存
     *
     * @param pk    主键
     * @param value 值
     * @param clazz model
     */
    public void setCahce(Object pk, Object value, Class<? extends Model> clazz) {
        String key = clazz.getSimpleName() + ":" + pk;
        try (Jedis jedis = JedisUtils.getJedis()) {
            if (null != jedis) {
                jedis.set(key, JSON.toJSONString(value));
                jedis.expire(key, Register.expire);
            }
        }
    }

    /**
     * 删除缓存
     *
     * @param pk    主键
     * @param clazz model
     */
    public void delCahce(Object pk, Class<? extends Model> clazz) {
        String key = clazz.getSimpleName() + ":" + pk;
        try (Jedis jedis = JedisUtils.getJedis()) {
            if (null != jedis) {
                jedis.del(key);
                jedis.expire(key, Register.expire);
            }
        }
    }
}
