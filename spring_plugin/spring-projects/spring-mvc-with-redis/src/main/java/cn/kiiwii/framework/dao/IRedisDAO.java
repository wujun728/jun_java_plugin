package cn.kiiwii.framework.dao;

import cn.kiiwii.framework.controller.User;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2016/9/19.
 */
public interface IRedisDAO {
    Object get(String name);

    Object getList(String name);

    Object getSet(String name);

    Object getZSet(String name);

    void setValue(String key, Object value);

    void setHash(String key, Map<String,? extends Object> map);

    Object getHashValue(String mapName,String key);

    Map<String,? extends Object> getHash(String key);

    void setList(String key, List<? extends Object> o);

    void setSet(String name, User user);

    void setZSet(String name, List<User> users);
}
