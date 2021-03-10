package com.zengtengpeng.operation;

import com.zengtengpeng.properties.RedissonProperties;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 操作对象
 */
public class RedissonObject {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private RedissonProperties redissonProperties;

    /**
     * 获取对象值
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getValue(String name) {
        RBucket<T> bucket = redissonClient.getBucket(name);
        return bucket.get();
    }

    /**
     * 获取对象空间
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> RBucket<T> getBucket(String name) {
        return redissonClient.getBucket(name);
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @return
     */
    public <T> void setValue(String name, T value) {
        setValue(name,value,redissonProperties.getDataValidTime());
    }

    /**
     * 设置对象的值
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒 -1 永久缓存
     * @return
     */
    public <T> void setValue(String name, T value, Long time) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        if(time==-1){
            bucket.set(value);
        }else {
            bucket.set(value, time, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 如果值已经存在则则不设置
     *
     * @param name  键
     * @param value 值
     * @param time  缓存时间 单位毫秒
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> Boolean trySetValue(String name, T value, Long time) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        boolean b;
        if(time==-1){
            b = bucket.trySet(value);
        }else {
            b = bucket.trySet(value, time, TimeUnit.MILLISECONDS);
        }
        return b;
    }

    /**
     * 如果值已经存在则则不设置
     *
     * @param name  键
     * @param value 值
     * @return true 设置成功,false 值存在,不设置
     */
    public <T> Boolean trySetValue(String name, T value) {
        return trySetValue(name,value,redissonProperties.getDataValidTime());
    }

    /**
     * 删除对象
     *
     * @param name 键
     * @return true 删除成功,false 不成功
     */
    public Boolean delete(String name) {
        return redissonClient.getBucket(name).delete();
    }




}
