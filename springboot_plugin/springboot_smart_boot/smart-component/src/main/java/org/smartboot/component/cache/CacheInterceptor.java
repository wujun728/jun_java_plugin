package org.smartboot.component.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.integration.cache.CacheClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 缓存拦截器,通过配置化拦截设置于接口处的注解
 * 
 * @author Wujun
 * @version CacheInterceptor.java, v 0.1 2016年3月22日 上午10:33:37 Seer Exp. 
 */
public class CacheInterceptor implements MethodInterceptor {

	/** key间隔符 */
	public static final String KEY_SEPERATOR = "_";

	/**
	 * logger
	 */
	private static final Logger LOGGER = LogManager.getLogger(CacheInterceptor.class);

	/**
	 * 缓存管理器
	 */
	private CacheClient cacheClient;

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		// 获取方法上的注解
		Method method = methodInvocation.getMethod();
		Cached cachedAnn = method.getAnnotation(Cached.class);
		if (cachedAnn == null) {
			return methodInvocation.proceed();
		}

		String key = null;
		try {
			key = getCacheKey(methodInvocation, cachedAnn);
		} catch (Exception ex) {
			LOGGER.warn("组装缓存Key异常", ex);
			return methodInvocation.proceed();
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("组装缓存查询KEY完成,method[" + method.getDeclaringClass().getName() + "." + method.getName()
				+ "],key[" + key + "]");
		}

		if (cachedAnn.operateType() != OperationEnum.READ) {
			// 不管最后是成功还是失败， 都优先清理缓存
			Long effectNums = removeFromCache(key);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("清理缓存,method[" + method.getDeclaringClass().getName() + "." + method.getName() + "],key["
					+ key + "],结果影响记录数[" + effectNums + "]");
			}

			return methodInvocation.proceed();
		}

		// 读取
		Object value = readCachedObj(key);
		if (value != null) {
			// 如果从缓存获取到值， 则直接返回此值
			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("缓存命中,method[" + method.getDeclaringClass().getName() + "." + method.getName() + "],key["
					+ key + "],value[" + value + "]");
			}
			return value;
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("缓存miss,method[" + method.getDeclaringClass().getName() + "." + method.getName() + "],key["
				+ key + "]");
		}
		value = methodInvocation.proceed();
		if (value != null) {
			writeCache(key, value, cachedAnn.expire());
		}
		return value;
	}

	/**
	 * 组装cache key
	 *
	 * @param methodInvocation
	 * @param cachedAnn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getCacheKey(MethodInvocation methodInvocation, Cached cachedAnn) {
		Method method = methodInvocation.getMethod();

		StringBuilder keyBuffer = new StringBuilder(cachedAnn.prefix().getPrefix());

		if (!StringUtils.isBlank(cachedAnn.staticKey())) {
			keyBuffer.append(KEY_SEPERATOR).append(cachedAnn.staticKey());
		}

		Object[] args = methodInvocation.getArguments();
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] paramAnns = method.getParameterAnnotations();
		if (paramAnns != null && paramAnns.length > 0) {
			for (int i = 0; i < paramAnns.length; i++) {
				if (paramAnns[i] == null || paramAnns[i].length == 0) {
					continue;
				}
				for (int j = 0; j < paramAnns[i].length; j++) {
					Annotation ann = paramAnns[i][j];
					if (!(ann instanceof CacheKey)) {
						continue;
					}

					Object param = args[i];
					if (param == null) {
						keyBuffer.append(KEY_SEPERATOR).append(StringUtils.EMPTY);
						continue;
					}

					Class<?> parameterType = parameterTypes[i];
					if (BeanUtils.isSimpleProperty(parameterType)) {
						keyBuffer.append(KEY_SEPERATOR).append(param);
						continue;
					}

					CacheKey keyFlag = (CacheKey) ann;
					String[] fieldNames = keyFlag.fields();
					if (fieldNames == null || fieldNames.length == 0) {
						keyBuffer.append(KEY_SEPERATOR)
							.append(ToStringBuilder.reflectionToString(param, ToStringStyle.SHORT_PREFIX_STYLE));
						continue;
					}

					if (Map.class.isAssignableFrom(parameterType)) {
						for (String fieldName : fieldNames) {
							Map<Object, Object> map = (Map<Object, Object>) param;
							keyBuffer.append(KEY_SEPERATOR).append(map.get(fieldName));
						}
					} else {
						BeanWrapper wrapper = new BeanWrapperImpl(param);
						for (String fieldName : fieldNames) {
							if (!wrapper.isReadableProperty(fieldName)) {
								keyBuffer.append(KEY_SEPERATOR).append(StringUtils.EMPTY);
							} else {
								Object propertyValue = wrapper.getPropertyValue(fieldName);
								keyBuffer.append(KEY_SEPERATOR)
									.append(propertyValue == null ? StringUtils.EMPTY : propertyValue);
							}
						}
					}
				}
			}
		}

		return keyBuffer.toString();
	}

	/**
	 * 写入cache
	 *
	 * @param key
	 * @param value
	 */
	private void writeCache(String key, Object value, int expire) {
		try {
			cacheClient.putObject(key, value, expire);
		} catch (Exception ex) {
			LOGGER.warn("系统异常:更新缓存对象失败,cacheKey=" + key, ex);
		}
	}

	/**
	 * 从cache中读取
	 *
	 * @param key
	 * @return
	 */
	private Object readCachedObj(String key) {
		Object value = null;
		try {
			value = cacheClient.getObject(key);
		} catch (Exception ex) {
			LOGGER.warn("系统异常:获取缓存对象失败,cacheKey=" + key, ex);
		}
		return value;
	}

	/**
	 * 清除cache
	 *
	 * @param cacheKey
	 * @return
	 */
	private Long removeFromCache(String cacheKey) {
		Long result = 0l;
		try {
			result = cacheClient.remove(cacheKey);
		} catch (Exception e) {
			LOGGER.warn("系统异常：删除缓存对象失败,cacheKey=" + cacheKey, e);
		}
		return result;
	}

	/**
	 * Setter method for property <tt>cacheClient</tt>.
	 *
	 * @param cacheClient
	 *            value to be assigned to property cacheClient
	 */
	public void setCacheClient(CacheClient cacheClient) {
		this.cacheClient = cacheClient;
	}

}
