package com.zengtengpeng.operation;

import com.zengtengpeng.properties.RedissonProperties;
import org.redisson.api.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 操作集合
 */
public class RedissonCollection {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedissonProperties redissonProperties;
    /**
     * 获取map集合
     * @param name
     * @param <K>
     * @param <V>
     * @return
     */
    public <K,V> RMap<K, V> getMap(String name){
       return redissonClient.getMap(name);
    }

    /**
     * 设置map集合
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setMapValues(String name, Map data,Long time){
        RMap map = redissonClient.getMap(name);
        Long dataValidTime = redissonProperties.getDataValidTime();
        if(time!=-1){
            map.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        map.putAll(data);
    }
    /**
     * 设置map集合
     * @param name
     * @param data
     * @return
     */
    public void setMapValues(String name, Map data){
        setMapValues(name,data,redissonProperties.getDataValidTime());
    }

    /**
     * 获取List集合
     * @param name
     * @return
     */
    public <T> RList<T> getList(String name){
       return redissonClient.getList(name);
    }

    /**
     * 设置List集合
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setListValues(String name, List data, Long time){
        RList list = redissonClient.getList(name);
        Long dataValidTime = redissonProperties.getDataValidTime();
        if(time!=-1){
            list.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        list.addAll(data);
    }
    /**
     * 设置List集合
     * @param name
     * @param data
     * @return
     */
    public void setListValues(String name, List data){
        setListValues(name,data,redissonProperties.getDataValidTime());
    }
    /**
     * 获取set集合
     * @param name
     * @return
     */
    public <T> RSet<T> getSet(String name){
       return redissonClient.getSet(name);
    }

    /**
     * 设置set集合
     * @param name
     * @param data
     * @param time 缓存时间,单位毫秒 -1永久缓存
     * @return
     */
    public void setSetValues(String name, Set data, Long time){
        RSet set = redissonClient.getSet(name);
        Long dataValidTime = redissonProperties.getDataValidTime();
        if(time!=-1){
            set.expire(dataValidTime, TimeUnit.MILLISECONDS);
        }
        set.addAll(data);
    }
    /**
     * 设置set集合
     * @param name
     * @param data
     * @return
     */
    public void setSetValues(String name, Set data){
        setSetValues(name,data,redissonProperties.getDataValidTime());
    }





}
