package com.jun.plugin.common.utils;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtil {

	public static <T, K> Map<K, T> toMap(/* */Collection<T> collection,
			/*@ApiParam("Map键值lambda表达式") */Function<T, K> keyMapper) {
		return toMap(collection, keyMapper, item -> item);
	}

	/**
	 * @param collection
	 * @param keyMapper   E::getName 或 e->e.getName()
	 * @param valueMapper E::getName 或 e->e.getName() 或
	 *                    e->MapUtils.as("name",e.getName)
	 * @return
	 */
	//@ApiOperation("将对象集合转换为对象Map")
	public static <T, K, V> Map<K, V> toMap( Collection<T> collection,
			  Function<T, K> keyMapper,
			 Function<T, V> valueMapper) {
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
	}

	//@ApiOperation("将集合构建为树集合")
	public static <T, K> void buildTree( Collection<T> collection,
			Function<T, K> keyMapper,
			Function<T, K> parentMapper,
			Function<T, Collection<T>> getChildrenMapper,
			BiConsumer<T, Collection<T>> setChildrenMapper) {
		Map<K, T> map = toMap(collection, item -> {
			Collection<T> children = getChildrenMapper.apply(item);
			if (children == null) {
				children = new ArrayList<>();
				setChildrenMapper.accept(item, children);
			}
			return keyMapper.apply(item);
		});
		for (Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {
			T t = iterator.next();
			T parentT = map.get(parentMapper.apply(t));
			if (parentT != null) {
				Collection<T> children = getChildrenMapper.apply(parentT);
				children.add(t);
				iterator.remove();
			}
		}
	}

	public static void buildTree(Collection<Map> collection, String key, String parent) {
		String childrenKey = "children";
		buildTree(collection, item -> item.get(key), item -> item.get(parent), item -> getValue(item,childrenKey, new ArrayList<>()), null);
	}
	public static void buildTree(Collection<Map> collection, String key, String parent,String childrenKey) {
		buildTree(collection, item -> item.get(key), item -> item.get(parent), item -> getValue(item,childrenKey, new ArrayList<>()), null);
	}

	public static <T> T getValue(Map map, String key, T defaultValue) {
		T value = (T) map.get(key);
		if (StringUtils.isEmpty(value)) {
			value = defaultValue;
			map.put(key, value);
		}
		return value;
	}

}
