package cn.springmvc.jpa.common.redis;

import cn.springmvc.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
public interface UserRedisService {

    public void add(User user);

    public User getUser(String key);

}
