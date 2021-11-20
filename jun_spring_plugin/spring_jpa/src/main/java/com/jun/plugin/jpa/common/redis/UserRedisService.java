package com.jun.plugin.jpa.common.redis;

import com.jun.plugin.jpa.entity.User;

/**
 * @author Vincent.wang
 *
 */
public interface UserRedisService {

    public void add(User user);

    public User getUser(String key);

}
