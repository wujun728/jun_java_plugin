/*   
 * Project: OSMP
 * FileName: DefaultCacheKeyGenerator.java
 * version: V1.0
 */
package com.osmp.cache.core;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Description:默认KEY生成器
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午1:57:38
 */

public class DefaultCacheKeyGenerator implements CacheKeyGenerator {

	@Override
	public Object getKey(CacheableDefine cacheDefine, Object[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append(cacheDefine.getMethod().getDeclaringClass().getName() + "." + cacheDefine.getMethod().getName());
		if (args != null) {
			for (Object ob : args) {
				sb.append("_");
				String toString = ToStringBuilder.reflectionToString(ob);
				toString = toString.replaceAll("@.*?\\[", "[");
				sb.append(toString);
			}
		}
		return sb.toString();
	}
}
