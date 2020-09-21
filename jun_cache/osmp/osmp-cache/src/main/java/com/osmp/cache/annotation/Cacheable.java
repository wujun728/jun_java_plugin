/*   
 * Project: OSMP
 * FileName: Cacheable.java
 * version: V1.0
 */
package com.osmp.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.osmp.cache.core.CacheKeyGenerator;
import com.osmp.cache.core.CacheableDefine;
import com.osmp.cache.core.DefaultCacheKeyGenerator;

/**
 * Description: 缓存注解
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 上午10:47:10
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cacheable {

	/**
	 * 缓存方法的名称
	 * 
	 * @return
	 */
	public String name();

	/**
	 * 缓存key前缀
	 * 
	 * @return
	 */
	public String prefix() default "";

	/**
	 * 缓存key生成策略
	 * 
	 * @return
	 */
	public Class<? extends CacheKeyGenerator> cacheKeyGenerator() default DefaultCacheKeyGenerator.class;

	/**
	 * 存活时间 单位为秒<br>
	 * 即对象最新失效的最大时间
	 * 
	 * @return
	 */
	public int timeToLive() default 1800;

	/**
	 * 空闲时间 单位为秒<br>
	 * 即距离上一次访问该对象后空闲的时间，如果超过则失效.受timeToLive约束
	 * 
	 * @return
	 */
	public int timeToIdle() default 0;

	/**
	 * 状态 CacheableDefine.STATE_OPEN, CacheableDefine.STATE_CLOSE;
	 * 
	 * @return
	 */
	public int state() default CacheableDefine.STATE_OPEN;
}
