package net.oschina.j2cache.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 相关的配置信息
 * @author Wujun
 *
 */
@ConfigurationProperties(prefix = "j2cache")
public class J2CacheConfig {
    private String configLocation = "/j2cache.properties";
    
    /**
     * 是否开启spring cache缓存,注意:开启后需要添加spring.cache.type=GENERIC,将缓存类型设置为GENERIC
     */
    private Boolean openSpringCache = false;
    
    /**
     * 缓存清除模式，
     * active:主动清除，二级缓存过期主动通知各节点清除，优点在于所有节点可以同时收到缓存清除
     * passive:被动清除，一级缓存过期进行通知各节点清除一二级缓存，
     * blend:两种模式一起运作，对于各个节点缓存准确以及及时性要求高的可以使用，正常用前两种模式中一个就可
     */
    private String cacheCleanMode = "passive";
    
    /**
     * 是否允许缓存空值,默认:true
     */
    private boolean allowNullValues = true;

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

	public Boolean getOpenSpringCache() {
		return openSpringCache;
	}

	public void setOpenSpringCache(Boolean openSpringCache) {
		this.openSpringCache = openSpringCache;
	}

	public String getCacheCleanMode() {
		return cacheCleanMode;
	}

	public void setCacheCleanMode(String cacheCleanMode) {
		this.cacheCleanMode = cacheCleanMode;
	}

	public boolean isAllowNullValues() {
		return allowNullValues;
	}

	public void setAllowNullValues(boolean allowNullValues) {
		this.allowNullValues = allowNullValues;
	}
}
