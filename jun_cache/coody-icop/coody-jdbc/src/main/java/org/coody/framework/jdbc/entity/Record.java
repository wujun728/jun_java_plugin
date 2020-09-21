package org.coody.framework.jdbc.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.coody.framework.core.util.PropertUtil;

public class Record implements Map<String, Object> {
	private Map<String, Object> map = new HashMap<String, Object>();

	public Record() {
	}

	public Record(Map<String, Object> map) {
		if (map == null) {
			return;
		}
		this.map = map;
	}

	@Override
	public int size() {
		return map.size();
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public Object get(Object key) {
		return map.get(key);
	}
	@Override
	public Object put(String key, Object value) {
		return map.put(key, value);
	}
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		map.putAll(m);
	}
	@Override
	public Set<String> keySet() {
		return map.keySet();
	}
	@Override
	public Collection<Object> values() {
		return map.values();
	}
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return map.entrySet();
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public void clear() {
		map.clear();
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Object parsBean(Class<?> cla) {
		return PropertUtil.mapToModel(map,cla);
	}
}

    