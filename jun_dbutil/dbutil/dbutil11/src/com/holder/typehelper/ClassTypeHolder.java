package com.holder.typehelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.holder.Assert;
import com.holder.log.ConsoleLog;

public abstract class ClassTypeHolder {

	@SuppressWarnings("unchecked")
	private static Map<Class, ParamerHolder> class_container = new HashMap<Class, ParamerHolder>();

	@SuppressWarnings("unchecked")
	public static Map<String, Method> getAllWriter(Class cla) {
		if (class_container.containsKey(cla)) {
			return class_container.get(cla).getAllWriter();
		}
		ParamerHolder parameterHolder = new ParamerHolder(cla);
		class_container.put(cla, parameterHolder);
		return parameterHolder.getAllWriter();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Method> getAllReader(Class cla) {
		if (class_container.containsKey(cla)) {
			return class_container.get(cla).getAllReader();
		}
		ParamerHolder parameterHolder = new ParamerHolder(cla);
		class_container.put(cla, parameterHolder);
		return parameterHolder.getAllReader();
	}

}

class ParamerHolder {
	@SuppressWarnings("unchecked")
	private Class cla;

	private Map<String, Method> readMethodContainer;

	private Map<String, Method> writeMethodContainer;

	private Method[] allMethod = null;

	private Field[] allField = null;

	@SuppressWarnings("unchecked")
	ParamerHolder(Class cla) {
		Assert.notNull(cla);
		this.cla = cla;
		ConsoleLog.debug("initialize a ClassTypeHolder with :" + this.cla);
		initialize();
	}

	void initialize() {
		this.allField = cla.getDeclaredFields();
		this.allMethod = cla.getDeclaredMethods();
		this.readMethodContainer = getReadMethodFromClass();
		this.writeMethodContainer = getWriteMethodFromClass();
	}

	private Map<String, Method> getWriteMethodFromClass() {
		Map<String, Method> result = new HashMap<String, Method>();
		for (Field field : allField) {
			String key = field.getName().toUpperCase();
			Method value = findWriteMethod(key);
			result.put(key, value);
		}
		ConsoleLog.debug("|ParamerHolder|getWriteMethodFromClass|" + result);
		return result;
	}

	private Map<String, Method> getReadMethodFromClass() {
		Map<String, Method> result = new HashMap<String, Method>();
		for (Field field : allField) {
			String key = field.getName().toUpperCase();
			Method value = findReadMethod(key);
			result.put(key, value);
		}
		return result;
	}

	/**
	 * 根据给定的字段名查找相应的写入方法
	 * 
	 * @param fieldName
	 * @return
	 */
	private Method findWriteMethod(String fieldName) {
		fieldName = fieldName.toUpperCase();
		for (Method method : allMethod) {
			if (method.getName().startsWith("set")
					&& method.getName().toUpperCase().equalsIgnoreCase("set"+fieldName)) {
				return method;
			}
		}
		return null;
	}

	private Method findReadMethod(String fieldName) {
		fieldName = fieldName.toUpperCase();
		for (Method method : allMethod) {
			if (method.getName().startsWith("get")
					&& method.getName().toUpperCase().equalsIgnoreCase("get"+fieldName)) {
				return method;
			}
		}
		return null;
	}

	public Map<String, Method> getAllWriter() {
		return this.writeMethodContainer;
	}

	public Map<String, Method> getAllReader() {
		return this.readMethodContainer;
	}
}
