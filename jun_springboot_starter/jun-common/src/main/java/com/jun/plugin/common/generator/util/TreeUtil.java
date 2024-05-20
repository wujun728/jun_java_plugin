package com.jun.plugin.common.generator.util;

import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeUtil {

	@SuppressWarnings("unchecked")
	//@ApiOperation("任意对象转为集合(ArrayList)")
	public static List<Object> toList(@ApiParam("任意对象") Object object) {
		List<Object> list = new ArrayList<>();
		if (object instanceof Collection)
			list.addAll((Collection<Object>) object);
		else if (object != null)
			if (object.getClass().isArray())
				list.addAll(TreeUtil.as((Object[]) object));
			else
				list.add(object);
		return list;
	}

	@SuppressWarnings("unchecked")
	//@ApiOperation("对象数组转为集合(ArrayList)")
	public static <T> List<T> as(@ApiParam("对象数组") T... array) {
		List<T> list = new ArrayList<T>(array.length);
		for (T t : array)
			list.add(t);
		return list;
	}

	//@ApiOperation("将对象集合转换为对象Map")
	public static <T, K> Map<K, T> toMap(@ApiParam("集合") Collection<T> collection,
			@ApiParam("Map键值lambda表达式") Function<T, K> keyMapper) {
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
	public static <T, K, V> Map<K, V> toMap(@ApiParam("集合") Collection<T> collection,
			@ApiParam("Map键值lambda表达式") Function<T, K> keyMapper,
			@ApiParam("新对象lambda表达式") Function<T, V> valueMapper) {
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
	}

	//@ApiOperation("将集合构建为树集合")
	public static <T, K> void buildTree(@ApiParam("集合") Collection<T> collection,
			@ApiParam("key值lambda表达式") Function<T, K> keyMapper,
			@ApiParam("parent值lambda表达式") Function<T, K> parentMapper,
			@ApiParam("获取子集合lambda表达式") Function<T, Collection<T>> getChildrenMapper,
			@ApiParam("设置子集合lambda表达式") BiConsumer<T, Collection<T>> setChildrenMapper) {
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
