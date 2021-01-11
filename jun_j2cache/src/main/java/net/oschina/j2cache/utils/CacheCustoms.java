package net.oschina.j2cache.utils;

/**
 * 缓存中用到的一些常量
 *
 * @author FY
 */
public interface CacheCustoms {

    /** 缓存操作 - 删除 */
    byte OPT_DELTED_KEY = 0x01;

    /** 一级缓存标识 */
    byte CACHE_LV_1 = 1;
    /** 二级缓存标识 */
    byte CACHE_LV_2 = 2;

}
