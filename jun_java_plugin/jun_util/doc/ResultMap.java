package org.myframework.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 使用“装饰器”模式来使用 MAP
 */
public class ResultMap<K, V> implements Map<K, V> {

	public static final int DEFAULT_NUMBER = 0;

	public static final String EMPTY_STRING = "";

	private static final Log log = LogFactory.getLog(ResultMap.class);

	private Map<K, V> map;

	/**
	 * 默认 clone map
	 * 
	 * @param map
	 */
	public ResultMap(Map<K, V> map) {
		this.map = map;
		this.initIdx();
	}

	/**
	 * 将数据转化为对应的索引数据类型
	 */
	private void initIdx() {
		if (this.containsKey("latnId")) {
			this.valueToNumber((K) "latnId");
		}
		if (this.containsKey("billingCycleId")) {
			this.valueToNumber((K) "billingCycleId");
		}
	}

	public ResultMap() {
		super();
	}

	public void setMap(Map<K, V> map) {
		this.map = map;
	}

	/**
	 * 删除Key
	 * 
	 * @param keys
	 * @return
	 */
	public Object[] removes(Object[] keys) {
		List<Object> removedKeys = new ArrayList<Object>();
		for (int i = 0; i < keys.length; i++) {
			Object key = keys[i];
			if (this.map.containsKey(key)) {
				removedKeys.add(this.map.remove(key));
			}
		}
		return removedKeys.toArray();
	}

	/**
	 * 转换为HashMap
	 * 
	 * @return
	 */
	public HashMap<K, V> toHashMap() {
		return new HashMap<K, V>(this.map);
	}

	/**
	 * 获取字符串
	 * 
	 * @param key
	 * @return
	 */
	public String getString(Object key) {
		return hasKey(key) ? map.get(key).toString().trim() : EMPTY_STRING;
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean hasKey(Object key) {
		return map.containsKey(key);
	}


	/**
	 * 转化STRING 为 NUMBER(ORACLE索引优化)
	 * 
	 * @param key
	 * @return
	 */
	public void valueToNumber(K key) {
		this.put(key, (V) Long.valueOf(this.getLong(key)));
	}

	/**
	 * 转化为STRING (ORACLE索引优化)
	 * 
	 * @param key
	 * @return
	 */
	public void valueToString(K key) {
		this.put(key, (V) this.getString(key));
	}

	/**
	 * 获取整型数
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(K key) {
		Object value = get(key);
		if (value instanceof String) {
			String stringValue = getString(key);
			return Integer.parseInt(stringValue);
		} else if (value instanceof Integer) {
			return ((Integer) value).intValue();
		} else {
			return DEFAULT_NUMBER;
		}
	}

	public long getLong(K key) {
		Object value = get(key);
		if (value instanceof String) {
			String stringValue = getString(key);
			return Long.parseLong(stringValue);
		} else if (value instanceof Long) {
			return ((Long) value).longValue();
		} else if (value instanceof BigDecimal) {
			return ((BigDecimal) value).longValue();
		} else {
			return DEFAULT_NUMBER;
		}
	}

	public double getDouble(K key) {
		Object value = get(key);
		if (value instanceof String) {
			String stringValue = getString(key);
			return Double.parseDouble(stringValue);
		} else if (value instanceof Double) {
			return ((Double) value).doubleValue();
		} else {
			return DEFAULT_NUMBER;
		}
	}

	public BigDecimal getBigDecimal(K key) {
		Object value = get(key);
		if (value instanceof String) {
			String stringValue = getString(key);
			return new BigDecimal(stringValue);
		} else if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		} else {
			return null;
		}
	}

	public boolean getBoolean(K key) {
		return getString(key).equals(Boolean.TRUE.toString());
	}

	/**
	 * 获取日期字符串 以pattern格式化
	 * 
	 * @param key
	 * @return
	 */
	public String getDateString(K key, String pattern) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		Object value = this.map.get(key);
		return dateFormat.format((Date) value);
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsKey(value);
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return this.map.entrySet();
	}

	@Override
	public boolean equals(Object obj) {
		return this.map.equals(obj);
	}

	public V get(Object key) {
		return this.map.get(key);
	}

	@Override
	public int hashCode() {
		return this.map.hashCode();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set<K> keySet() {
		return this.map.keySet();
	}

	public V put(K key, V value) {
		return this.map.put(key, value);
	}

	public void putAll(Map<? extends K, ? extends V> t) {
		this.map.putAll(t);
	}

	public V remove(Object key) {
		return this.map.remove(key);
	}

	public int size() {
		return this.map.size();
	}

	@Override
	public String toString() {
		return this.map.toString();
	}

	public Collection<V> values() {
		return this.map.values();
	}

}
