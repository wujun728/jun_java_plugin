package org.coody.framework.web.container;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.coody.framework.web.entity.MvcMapping;

@SuppressWarnings("unchecked")
public class MappingContainer {

	private static Map<String, Object> MVC_MAP = new ConcurrentHashMap<String, Object>();

	public static <T> T getMapping(String path) {
		return (T) MVC_MAP.get(path);
	}

	public static void writeMapping(MvcMapping mvcMapping) {
		MVC_MAP.put(mvcMapping.getPath(), mvcMapping);
	}

	public static boolean containsPath(String path) {
		return MVC_MAP.containsKey(path);
	}

	public static Collection<?> getBeans() {
		return MVC_MAP.values();
	}

	
}
