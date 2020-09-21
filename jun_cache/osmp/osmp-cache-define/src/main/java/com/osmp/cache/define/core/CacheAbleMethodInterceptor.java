/*   
 * Project: OSMP
 * FileName: CacheAbleMethodInterceptor.java
 * version: V1.0
 */
package com.osmp.cache.define.core;

import java.lang.reflect.Method;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

/**
 * Description: 缓存拦截器
 * 
 * @author: wangkaiping
 * @date: 2014年8月15日 上午10:18:22
 */

public class CacheAbleMethodInterceptor implements MethodInterceptor, InitializingBean, Ordered {
	private CacheManagerService cacheManagerService;

	public void setCacheManagerService(CacheManagerService cacheManagerService) {
		this.cacheManagerService = cacheManagerService;
	}

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Method method = methodInvocation.getMethod();
		String defineId = getMethodId(method);
		CacheableDefine cacheableDefine = cacheManagerService.getCacheableDefine(defineId);
		if (cacheableDefine != null && cacheableDefine.getState() == CacheableDefine.STATE_OPEN
				&& cacheManagerService.isOpen()) {
			Object key = cacheableDefine.getCacheKey(methodInvocation.getArguments());
			Cache cache = cacheManagerService.getDefaultCache();
			Element el = cache.get(key);
			if (el == null) {
				Object obj = methodInvocation.proceed();
				el = new Element(key, obj);
				el.setTimeToIdle(cacheableDefine.getTimeToIdle());
				el.setTimeToLive(cacheableDefine.getTimeToLive());
				el.setEternal(false);
				cache.put(el);
				return obj;
			} else {
				return el.getObjectValue();
			}
		} else {
			return methodInvocation.proceed();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheManagerService == null) {
			throw new NullPointerException("cacheManageService属性不能为null");
		}
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}

	private String getMethodId(Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(method.getDeclaringClass().getName() + "." + method.getName() + "(");
		for (Class<?> type : method.getParameterTypes()) {
			sb.append(type.getName() + ",");
		}

		return DigestUtils.md5Hex(sb.toString());
	}
}
