package com.gosalelab.constants;

/**
 * aspect cache's constants
 * @author Wujun
 */
public class CacheConstants {
    public final static String CACHE_EHCACHE_NAME = "eh";
    public final static String CACHE_REDIS_NAME = "redis";

    public final static String DEFAULT_CACHE_KEY_GENERATE_NAME = "default";

    public final static String DEFAULT_CACHE_PROVIDER = CACHE_EHCACHE_NAME;

    public final static int TIME_TO_IDLE_EXPIRATION = 1800;

    public final static int TIME_TO_LIVE_EXPIRATION = 3600;

    public final static String SINGLE_QUOTATION_MARK ="'";

    public final static String POUND_SIGN ="#";

    public final static String RETURN_VALUE = POUND_SIGN + "retVal";

    public static final String ASPECT_CACHE_PREFIX = "com.gosalelab.cache";

    public static final String MAIN_PACKAGE = "com.gosalelab";

}
