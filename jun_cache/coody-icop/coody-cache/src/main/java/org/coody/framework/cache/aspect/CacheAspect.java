package org.coody.framework.cache.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.coody.framework.cache.annotation.CacheWipe;
import org.coody.framework.cache.annotation.CacheWipes;
import org.coody.framework.cache.annotation.CacheWrite;
import org.coody.framework.cache.instance.iface.IcopCacheFace;
import org.coody.framework.core.annotation.Around;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.point.AspectPoint;
import org.coody.framework.core.util.MethodSignUtil;
import org.coody.framework.core.util.StringUtil;

@AutoBuild
public class CacheAspect {

	
	@AutoBuild
	IcopCacheFace cacheable;
	
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 写缓存操作
	 * 
	 * @param aspect
	 * @return 方法返回内容
	 * @throws Throwable
	 */
	@Around(annotationClass=CacheWrite.class)
	public Object cCacheWrite(AspectPoint aspect) throws Throwable {
		Class<?> clazz = aspect.getClazz();
		Method method = aspect.getMethod();
		if (method == null) {
			return aspect.invoke();
		}
		// 获取注解
		CacheWrite handle = method.getAnnotation(CacheWrite.class);
		if (handle == null) {
			return aspect.invoke();
		}
		// 封装缓存KEY
		Object[] paras = aspect.getParams();
		String key = handle.key();
		try {
			if (StringUtil.isNullOrEmpty(key)) {
				key = MethodSignUtil.getMethodKey(clazz, method);
			}
			if (StringUtil.isNullOrEmpty(handle.fields())) {
				String paraKey = MethodSignUtil.getBeanKey(paras);
				if (!StringUtil.isNullOrEmpty(paraKey)) {
					key += ":";
					key += paraKey;
				}
			}
			if (!StringUtil.isNullOrEmpty(handle.fields())) {
				key = MethodSignUtil.getFieldKey(clazz, method, paras, key, handle.fields());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer cacheTimer = ((handle.time() == 0) ? 24 * 3600 : handle.time());
		// 获取缓存
		try {
			Object result = cacheable.getCache(key);
			logger.debug("获取缓存 >>" + key + ",结果:" + result);
			if (!StringUtil.isNullOrEmpty(result)) {
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object result = aspect.invoke();
		if (result != null) {
			try {
				cacheable.setCache(key, result, cacheTimer);
				logger.debug("设置缓存 >>" + key + ",结果:" + result + ",缓存时间:" + cacheTimer);
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 缓存清理
	 * 
	 * @param aspect
	 * @return 方法返回内容
	 * @throws Throwable
	 */
	@Around(annotationClass=CacheWipes.class)
	@Around(annotationClass=CacheWipe.class)
	public Object zCacheWipe(AspectPoint aspect) throws Throwable {
		Class<?> clazz = aspect.getClazz();
		Method method = aspect.getMethod();
		if (method == null) {
			return aspect.invoke();
		}
		Object[] paras = aspect.getParams();
		Object result = aspect.invoke();
		CacheWipe[] handles = method.getAnnotationsByType(CacheWipe.class);
		if (StringUtil.isNullOrEmpty(handles)) {
			return result;
		}
		for (CacheWipe handle : handles) {
			try {
				String key = handle.key();
				if (StringUtil.isNullOrEmpty(handle.key())) {
					key = (MethodSignUtil.getMethodKey(clazz, method));
				}
				if (!StringUtil.isNullOrEmpty(handle.fields())) {
					key = MethodSignUtil.getFieldKey(clazz, method, paras, key, handle.fields());
				}
				logger.debug("删除缓存 >>" + key);
				cacheable.delCache(key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
