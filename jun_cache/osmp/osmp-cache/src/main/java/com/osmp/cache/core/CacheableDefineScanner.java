/*   
 * Project: OSMP
 * FileName: CacheableDefineScanner.java
 * version: V1.0
 */
package com.osmp.cache.core;

import java.lang.reflect.Method;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;

import com.osmp.cache.annotation.Cacheable;

/**
 * Description: 扫描方法获取cachedefine 当BEAN初始化完成的时候，将扫描带有缓存注解的BEAN并将相关信息存储。
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午2:00:30
 */

public class CacheableDefineScanner implements BeanPostProcessor, InitializingBean {
	private final static Logger log = Logger.getLogger(CacheableDefineScanner.class);

	private CacheManager cacheManager;

	public CacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (cacheManager == null) {
			throw new NullPointerException("cacheManage属性不能为null");
		}

	}

	@Override
	public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
		return arg0;
	}

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
		ReflectionUtils.doWithMethods(arg0.getClass(), new MethodCallback() {
			@Override
			public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
				Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
				if (cacheable != null) {

					if (null == cacheable.name() || "".equals(cacheable.name())) {
						log.warn("Cacheable name is empty," + method.getDefaultValue());
					}

					Class<? extends CacheKeyGenerator> clazz = cacheable.cacheKeyGenerator();
					if (clazz == null) {
						log.warn("cacheKeyGenerator is null use default generator DefaultCacheKeyGenerator");
						clazz = DefaultCacheKeyGenerator.class;
					}

					CacheableDefine cacheableDefine = new CacheableDefine();
					cacheableDefine.setMethod(method);
					cacheableDefine.setName(cacheable.name());
					cacheableDefine.setTimeToIdle(cacheable.timeToIdle());
					cacheableDefine.setTimeToLive(cacheable.timeToLive());
					cacheableDefine.setState(cacheable.state());
					cacheableDefine.setId(getMethodId(method));
					try {
						cacheableDefine.setCacheKeyGenerator(clazz.newInstance());
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					cacheManager.putCacheableDefine(cacheableDefine);
				}

			}
		}, ReflectionUtils.USER_DECLARED_METHODS);
		return arg0;
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
