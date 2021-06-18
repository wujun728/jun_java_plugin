package cn.springmvc.jpa.common.redis;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.springmvc.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
@Service
public class UserRedisServiceImpl extends RedisBaseService<String, User> implements UserRedisService {

    @Override
    public void add(User user) {
        ValueOperations<String, User> valueops = redisTemplate.opsForValue();
        valueops.set(user.getId(), user);
    }

    @Override
    public User getUser(String key) {
        ValueOperations<String, User> valueops = redisTemplate.opsForValue();
        return valueops.get(key);
    }

}
