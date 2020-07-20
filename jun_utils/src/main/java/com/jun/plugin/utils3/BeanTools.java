package com.jun.plugin.utils3;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * java反射工具类
 * 
 * @since 2015-11-26
 * @author klguang
 * 
 */
public class BeanTools {
	/**
	 * 
	 * @param bean
	 *            需要转换位map的bean
	 * @param allowedNull
	 *            是否允许属性值为null
	 * @return Map<String, Object> 属性集合和bean的class信息
	 */
	private static Map<String, Object> convertBean(Object bean,
			boolean allowedNull) {
		if (bean == null)
			return null;
		Map<String, Object> properties = new HashMap<String, Object>();
		try {
			// 在 Java Bean 上进行内省，了解其所有属性、公开的方法和事件。
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			// javaBean一个属性以及其getter、setter方法
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			if (propertyDescriptors != null) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					// 判空是必须的，一个属性可能没有getter、setter方法
					if (propertyDescriptor != null) {
						String fieldName = propertyDescriptor.getName();
						Method readMethod = propertyDescriptor.getReadMethod();
						if (readMethod != null) {
							try {
								// 获取字段的值
								Object fieldValue = readMethod.invoke(bean);
								if (allowedNull == true)
									properties.put(fieldName, fieldValue);
								else if (fieldValue != null)
									properties.put(fieldName, fieldValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 
	 * @param bean
	 * @return Map<String, Object> 所有属性集合和bean的class信息
	 */

	public static Map<String, Object> convertBean(Object bean) {
		return convertBean(bean, true);
	}

	/**
	 * 
	 * @param bean
	 *            需要转换位map的bean
	 * @param allowedNull
	 *            是否允许属性值为null
	 * @return Map<String, Object> 属性集合 包括属性值为null
	 */
	public static Map<String, Object> transToMap(Object bean,
			boolean allowedNull) {
		Map<String, Object> target = convertBean(bean, allowedNull);
		target.remove("class");
		return target;
	}

	/**
	 * 
	 * @param bean
	 * @param allowedNull
	 *            是否允许属性值为空
	 * @param fieldNames
	 *            需要包括的属性名
	 * @return Map<String, Object> 属性集合
	 */
	public static Map<String, Object> transToMapInclude(Object bean,
			boolean allowedNull,
			String... fieldNames) {
		if (bean == null)
			return null;
		Map<String, Object> properties = new HashMap<String, Object>();
		try {
			for (String fieldName : fieldNames) {
				Object fieldValue = PropertyUtils.getProperty(bean, fieldName);// 得到此属性的值
				if (allowedNull == true)
					properties.put(fieldName, fieldValue);
				else if (fieldValue != null)
					properties.put(fieldName, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 此方法效率比transToMapInclude要高
	 * 
	 * @param bean
	 * @param allowedNull
	 *            是否允许属性值为空
	 * @param fieldNames
	 *            需要排除的属性名,默认排除serialVersionUID
	 * @return Map<String, Object> 属性集合
	 */
	public static Map<String, Object> transToMapExclude(Object bean,
			boolean allowedNull,
			String... fieldNames) {
		if (bean == null)
			return null;
		Map<String, Object> properties = new HashMap<String, Object>();
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector
					.getBeanInfo(bean.getClass()).getPropertyDescriptors();
			if (propertyDescriptors != null) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					// 判空是必须的，一个属性可能没有getter、setter方法
					if (propertyDescriptor != null) {
						String fieldName = propertyDescriptor.getName();
						Method readMethod = propertyDescriptor.getReadMethod();
						if (!ArrayUtils.contains(fieldNames, fieldName))
							continue;
						if (readMethod != null && !fieldName.equals("class")) {
							try {
								// 获取字段的值
								Object fieldValue = readMethod.invoke(bean);
								if (allowedNull == true)
									properties.put(fieldName, fieldValue);
								else if (fieldValue != null)
									properties.put(fieldName, fieldValue);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}

	// -----------------------------------------------------------------------------------------------

	/**
	 * 为对象的嵌套字段赋值
	 * 
	 * @param object
	 * @param fieldName
	 *            嵌套字段。eg:"address.city"
	 * @param value
	 */
	public static void setNestedField(Object object, String fieldName,
			Object value) {		
		String[] fieldNames = fieldName.split("\\.");
		if (fieldNames.length > 2)
			return;
		Class clazz = object.getClass();
		try {
			// 复杂字段
			PropertyDescriptor descriptor = PropertyUtils
					.getPropertyDescriptor(object, fieldNames[0]);
			Object complexFieldValue = descriptor.getReadMethod()
					.invoke(object);
			if (complexFieldValue == null) {// 判断是否被实例
				complexFieldValue = descriptor.getPropertyType().newInstance();
			}
			// 嵌套字段赋值
			PropertyUtils.setProperty(complexFieldValue, fieldNames[1], value);
			// 复杂字段赋值
			descriptor.getWriteMethod().invoke(object, complexFieldValue);
		} catch (SecurityException | InstantiationException
				| IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 为对象字段赋值,支持嵌套字段
	 * 
	 * @param object
	 * @param fieldName
	 *            要赋值的字段。eg:"name","address.city"
	 * @param value
	 */
	public static void setField(Object object, String fieldName, Object value) {
		if (fieldName.contains(".")) {
			setNestedField(object, fieldName, value);
		} else {
			try {
				PropertyUtils.setProperty(object, fieldName, value);
			} catch (IllegalAccessException | InvocationTargetException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 实例化对象，并对字段赋值，支持嵌套字段
	 * 
	 * @param clazz
	 *            要实例化的对象
	 * @param fieldName
	 *            要赋值的字段。eg:"name","address.city"
	 * @param value
	 * @return 被创建的对象
	 */
	public static <T> T newAndSet(Class<T> clazz, String fieldName, Object value) {
		T target = null;
		try {
			target = clazz.newInstance();
			setField(target, fieldName, value);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return target;
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * 对象拷贝 数据对象空值不拷贝到目标对象
	 * 
	 * @param dataObject
	 * @param toObject
	 * @throws NoSuchMethodException
	 *             copy
	 */
	public static void copyPropertyIgnoreNull(Object dest, Object orig){
		PropertyDescriptor origDescriptors[] =PropertyUtils.getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			// String type = origDescriptors[i].getPropertyType().toString();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(orig, name) &&
					PropertyUtils.isWriteable(dest, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(orig, name);
					if (value != null) {
						PropertyUtils.setProperty(dest, name, value);
					}
				} catch (java.lang.IllegalArgumentException ie) {
					; // Should not happen
				} catch (Exception e) {
					; // Should not happen
				}

			}
		}
	}
	
	
	
	public String getMethodQualifiedName(Method method){
		return method.getDeclaringClass().getName() + "." + method.getName()
				+"";
	}
	/**
	 * 相同类型的对象复制
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 */
	public static void copyProperties(Object target, Object source, String... ignoreProperties) {
		if(source.getClass() != target.getClass()){
			throw new IllegalArgumentException();
		}

		PropertyDescriptor[] propertyDescriptors = PropertyUtils
				.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null
					|| writeMethod == null) {
				continue;
			}
			try {
				Object sourceValue = readMethod.invoke(source);
				writeMethod.invoke(target, sourceValue);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}
}
