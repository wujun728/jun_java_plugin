/*   
 * Project: OSMP
 * FileName: CacheAbleMethodInterceptor.java
 * version: V1.0
 */
package com.osmp.cache.interceptor;

import java.lang.reflect.Method;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import com.osmp.cache.core.CacheManager;
import com.osmp.cache.core.CacheableDefine;

/**
 * Description: 缓存拦截器
 * 
 * @author: wangkaiping
 * @date: 2014年8月15日 上午10:18:22
 */

public class CacheAbleMethodInterceptor implements MethodInterceptor, InitializingBean, Ordered {
	private CacheManager cacheManager;

	private Cache cache;

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		CacheableDefine cacheableDefine = cacheManager.getCacheableDefine(method);
		if (cacheableDefine != null && cacheableDefine.getState() == CacheableDefine.STATE_OPEN
				&& cacheManager.isOpen()) {
			Object key = cacheableDefine.getCacheKey(methodInvocation.getArguments());
			Element el = cache.get(key);
			if (el == null) {
				Object obj = methodInvocation.proceed();
				el = new Element(key, obj);
				el.setTimeToIdle(cacheableDefine.getTimeToIdle());
				el.setTimeToLive(cacheableDefine.getTimeToLive());
				cache.put(el);
				return obj;
			} else {
				return el.getObjectValue();
			}
		} else {
			return methodInvocation.proceed();
		}
	}

	/**
	 * @param cacheManager
	 *            the cacheManager to set
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * @param cache
	 *            the cache to set
	 */
	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cache == null) {
			throw new NullPointerException("cache属性不能为null");
		}

		if (cacheManager == null) {
			throw new NullPointerException("cacheManage属性不能为null");
		}
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
