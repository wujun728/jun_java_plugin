package org.coody.framework.core.container;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.exception.BeanConflictException;
import org.coody.framework.core.util.ClassUtil;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;

@SuppressWarnings({ "unchecked" })
public class BeanContainer {

	private static Map<String, Map<String, Object>> beanContainer = new ConcurrentHashMap<String, Map<String, Object>>();

	public static <T> T getBean(Class<?> cla) {
		String beanName = getBeanName(cla);
		if (StringUtil.isNullOrEmpty(beanName)) {
			return null;
		}
		Map<String, Object> map = beanContainer.get(beanName);
		if (StringUtil.isNullOrEmpty(map)) {
			return null;
		}
		String realBeanName = cla.getName();
		return (T) map.get(realBeanName);
	}

	public static <T> T getBean(String beanName) {
		if (StringUtil.isNullOrEmpty(beanName)) {
			return null;
		}
		Map<String, Object> map = beanContainer.get(beanName);
		if (StringUtil.isNullOrEmpty(map)) {
			return null;
		}
		if (map.size() > 1) {
			throw new BeanConflictException(beanName);
		}
		for (String key : map.keySet()) {
			return (T) map.get(key);
		}
		return null;
	}

	public static synchronized void setBean(String beanName, Object bean) {
		Class<?> clazz=bean.getClass();
		if (ClassUtil.isCglibProxyClassName(clazz.getName())) {
			clazz = clazz.getSuperclass();
		}
		String realBeanName = clazz.getName();
		if (beanContainer.containsKey(beanName)) {
			Map<String, Object> map = beanContainer.get(beanName);
			map.put(realBeanName, bean);
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(realBeanName, bean);
		beanContainer.put(beanName, map);
	}

	public static boolean contains(String beanName) {
		return beanContainer.containsKey(beanName);
	}

	public static Collection<?> getBeans() {
		HashSet<Object> beans = new HashSet<Object>();
		for (String key : beanContainer.keySet()) {
			Map<String, Object> map = beanContainer.get(key);
			if (StringUtil.isNullOrEmpty(map)) {
				continue;
			}
			for (String realKey : map.keySet()) {
				beans.add(map.get(realKey));
			}
		}
		return beans;
	}

	public static String getBeanName(Class<?> clazz) {
		if (StringUtil.isNullOrEmpty(clazz.getAnnotations())) {
			return null;
		}
		if (ClassUtil.isCglibProxyClassName(clazz.getName())) {
			clazz = clazz.getSuperclass();
		}
		List<Annotation> initBeans = PropertUtil.getAnnotations(clazz, AutoBuild.class);
		if (StringUtil.isNullOrEmpty(initBeans)) {
			return null;
		}
		for (Annotation annotation : initBeans) {
			if (StringUtil.isNullOrEmpty(annotation)) {
				continue;
			}
			String beanName = clazz.getName();
			Object value = PropertUtil.getAnnotationValue(annotation, "value");
			if (StringUtil.isNullOrEmpty(value) || value.getClass().isArray()) {
				return beanName;
			}
			if (!StringUtil.isNullOrEmpty(beanName)) {
				return beanName;
			}
			return clazz.getName();
		}
		return null;
	}

	public static List<String> getBeanNames(Class<?> clazz) {
		if (ClassUtil.isCglibProxyClassName(clazz.getName())) {
			clazz = clazz.getSuperclass();
		}
		Set<String> beanNames = new HashSet<String>();
		String beanName = getBeanName(clazz);
		if (StringUtil.isNullOrEmpty(beanName)) {
			return null;
		}
		beanNames.add(beanName);
		beanNames.add(clazz.getName());
		Class<?>[] clazzs = clazz.getInterfaces();
		if (!StringUtil.isNullOrEmpty(clazzs)) {
			for (Class<?> clazTemp : clazzs) {
				if (clazTemp.getName().startsWith("java.util")) {
					continue;
				}
				if (clazTemp.getName().startsWith("java.lang")) {
					continue;
				}
				if (clazTemp.getName().startsWith("java.net")) {
					continue;
				}
				beanNames.add(clazTemp.getName());
				beanName = getBeanName(clazTemp);
				if (StringUtil.isNullOrEmpty(beanName)) {
					continue;
				}
				beanNames.add(beanName);
				
			}
		}
		if (StringUtil.isNullOrEmpty(beanNames)) {
			return null;
		}
		return new ArrayList<String>(beanNames);
	}
}
