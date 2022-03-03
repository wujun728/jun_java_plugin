package com.jun.admin.framework;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Generics的util类. 来自www.springside.org.cn
 * 
 * 
 */
public class GenericsUtils {
	private static final Log log = LogFactory.getLog(GenericsUtils.class);

	private GenericsUtils() {
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager&lt;Book&gt;
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends
	 * GenricManager&lt;Book&gt;
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or <code>Object.class</code> if
	 *         cannot be determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index) {

		// Type getGenericSuperclass():返回本类的父类,包含泛型参数信息
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		// 返回表示此类型实际类型参数的 Type 对象的数组，获得超类的泛型参数的实际类型。。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * for batch delete
	 * 
	 * @param ids
	 * @return
	 */
	public static String createBlock(Object[] ids) {
		if (ids == null || ids.length == 0)
			return "('')";
		String blockStr = "";
		for (int i = 0; i < ids.length - 1; i++) {
			blockStr += "'" + ids[i] + "',";
		}
		blockStr += "'" + ids[ids.length - 1] + "'";
		return blockStr;
	}
}
