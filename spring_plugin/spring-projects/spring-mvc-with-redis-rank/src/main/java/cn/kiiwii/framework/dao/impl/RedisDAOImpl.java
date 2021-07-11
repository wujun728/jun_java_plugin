package cn.kiiwii.framework.dao.impl;

import cn.kiiwii.framework.dao.IRedisDAO;
import cn.kiiwii.framework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhong on 2016/9/19.
 */
@Repository("redisDAO")
public class RedisDAOImpl implements IRedisDAO {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Object get(String key) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object o = valueOperations.get(key);
        return o;
    }

    @Override
    public Object getSet(String name) {
        return null;
    }

    @Override
    public Object getZSet(String name) {
        return null;
    }

    @Override
    public void setValue(String key, Object value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    @Override
    public void setHash(String key, Map<String, ? extends Object> map) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key, map);
    }

    @Override
    public Object getHashValue(String mapName, String key) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Object o = null;
        if (hashOperations.hasKey(mapName, key)) {
            o = hashOperations.get(mapName, key);
        }
        return o;
    }

    @Override
    public Map<String, ? extends Object> getHash(String key) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Map<String, Object> map = hashOperations.entries(key);
        return map;
    }

    @Override
    public void setList(String key, List<? extends Object> os) {
        ListOperations<String,Object>  listOperations = redisTemplate.opsForList();
        for (Object o : os) {
            listOperations.leftPush(key, o);
        }
    }

    @Override
    public List getList(String key) {
        ListOperations listOperations = redisTemplate.opsForList();
        List o = null;
        if (listOperations.size(key) > 0) {
            o = (List) listOperations.range(key, 0, -1);
        }
        return o;
    }

    @Override
    public void setSet(String name, User user) {

    }

    @Override
    public void setZSet(String key,int id,double score) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.incrementScore(key,id,score);
    }

    @Override
    public void addScore(String key, int id, double score) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.incrementScore(key,id,score);
    }

    @Override
    public void updateScore(String key, int id, double score) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.add(key,id,score);
    }

    @Override
    public Set getTop(String key,int top) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        Set set = zSet.reverseRange(key,0,top-1);
        return set;
    }

    @Override
    public Set getTopWithScore(String key,int top) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        Set set = zSet.reverseRangeWithScores(key,0,top-1);
        return set;
    }

    @Override
    public Set getTopWithScore(String key, int start, int limit) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        Set set = zSet.reverseRangeWithScores(key,start,start+limit-1);
        return set;
    }
}
