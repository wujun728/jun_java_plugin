/*   
 * Project: OSMP
 * FileName: MapCreaterBuilder.java
 * version: V1.0
 */
package com.osmp.utils.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 下午1:49:39
 */
public class MapCreaterBuilder {
	private Map<String, Object> parameters;

	public static MapCreaterBuilder create() {
		return new MapCreaterBuilder();
	}

	public static MapCreaterBuilder create(String key, Object value) {
		return create().put(key, value);
	}

	public MapCreaterBuilder() {

	}

	public MapCreaterBuilder put(String key, Object value) {
		this.getParameters().put(key, value);
		return this;
	}

	public Map<String, Object> map() {
		return this.parameters;
	}

	protected Map<String, Object> getParameters() {
		if (null == parameters) {
			this.parameters = new HashMap<String, Object>();
		}
		return parameters;
	}
}
