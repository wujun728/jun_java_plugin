package net.oschina.j2cache.utils;

/**
 * @author Wujun
 */
public class CacheCodeUtils {

    /** 缓存KEY的组分隔符 */
    public static final String KEY_SEPARATOR_SIGN = ":";


    /**
     * 创建缓存 redis key
     * @param region 缓存的region name
     * @param key 标志位
     * @return 完整KEY - region:key - string
     */
    public static String createRedisKey(String region, Object key) {
        return String.format("%s%s%s", region, KEY_SEPARATOR_SIGN, key.toString());
    }

}
