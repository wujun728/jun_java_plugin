package cn.kiiwii.framework.dao;


import cn.kiiwii.framework.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhong on 2016/9/19.
 */
public interface IRedisDAO {
    Object get(String name);

    Object getList(String name);

    Object getSet(String name);

    Object getZSet(String name);

    void setValue(String key, Object value);

    void setHash(String key, Map<String, ? extends Object> map);

    Object getHashValue(String mapName, String key);

    Map<String,? extends Object> getHash(String key);

    void setList(String key, List<? extends Object> o);

    void setSet(String name, User user);

    void setZSet(String key,int id,double score);

    void addScore(String key,int id,double score);

    void updateScore(String key,int id,double score);

    public Set getTop(String key, int top);

    Set getTopWithScore(String key, int i);

    Set getTopWithScore(String key,int start, int limit);
}
