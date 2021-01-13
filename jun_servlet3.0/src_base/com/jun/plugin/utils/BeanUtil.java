package com.jun.plugin.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtil {
	private static final char SEPARATOR = '_';
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);
	protected static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param dest
	 *            目标对象，标准的JavaBean
	 * @param orig
	 *            源对象，可为Map、标准的JavaBean
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static void applyIf(Object dest, Object orig) throws Exception {
		try {
			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					if (PropertyUtils.isWriteable(dest, name)) {
						Object value = ((Map) orig).get(name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			} else {
				java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();
					if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value != null) {
							PropertyUtils.setSimpleProperty(dest, name, value);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}

	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object[] params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);
		method.setAccessible(true);
		return method.invoke(object, params);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName, Object param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod(object, methodName, new Object[] { param });
	}

	public static <T> T populate2(T t, Map<String, ? extends Object> param) {
		try {
			org.apache.commons.beanutils.BeanUtils.populate(t, param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}

	/**
	 * @param cls
	 * @param param
	 * @return
	 */
	public static <T> T populate(Class<T> cls, Map<String, ? extends Object> param) {
		T t = null;
		try {
			t = populate2(cls.newInstance(), param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Title: describe @Description: 获得同时有get和set的field和value。 @param bean
	 *         获得bean对象 @return Map 返回属性列表 @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map describe(Object bean) {
		Map des = new HashMap();
		PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
		String name = null;
		for (int i = 0; i < desor.length; i++) {
			if (desor[i].getReadMethod() != null && desor[i].getWriteMethod() != null) {
				name = desor[i].getName();
				try {
					des.put(name, PropertyUtils.getProperty(bean, name));
				} catch (Exception e) {
					throw new RuntimeException("属性不存在：" + name);
				}
			}
		}
		return des;
	}

	public static void setSimpleProperty(Object bean, String name, Object value) {
		try {
			PropertyUtils.setSimpleProperty(bean, name, value);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}

	public static Object setSimpleProperty(Object bean, String name) {
		try {
			return PropertyUtils.getSimpleProperty(bean, name);
		} catch (Exception e) {
			throw new RuntimeException("属性不存在：" + name);
		}
	}

	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * @Title: setFieldValue @Description:
	 *         直接设置对象属性值,无视private/protected修饰符,不经过setter函数. @param object
	 *         对象名 @param fieldName 字段名称 @param value 值 @param @throws
	 *         NoSuchFieldException @return void @throws
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * @Title: getDeclaredField @Description:
	 *         循环向上转型,获取对象的DeclaredField. @param @param object @param @param
	 *         fieldName @param @return @param @throws
	 *         NoSuchFieldException @return Field @throws
	 */
	public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * @Title: getDeclaredField @Description: 循环向上转型,获取类的DeclaredField. @param
	 *         clazz 类名称 @param fieldName 字段名称 @param @throws
	 *         NoSuchFieldException @return Field @throws
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}

	/**
	 * 将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性
	 * 
	 * @param orig
	 *            源对象，标准的JavaBean
	 * @param dest
	 *            排除检查的属性，Map
	 * 
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkObjProperty(Object orig, Map dest) throws Exception {
		try {
			java.lang.reflect.Field[] fields = orig.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (!dest.containsKey(name)) {
					if (PropertyUtils.isReadable(orig, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						if (value == null) {
							return true;
						}
					}
				}
			}
			return false;
		} catch (Exception e) {
			throw new Exception("将源对象中的值覆盖到目标对象中，仅覆盖源对象中不为NULL值的属性", e);
		}
	}

	/**
	 * 将属性样式字符串转成驼峰样式字符串 (例:branchNo -> branch_no)
	 * 
	 * @param inputString
	 * @return
	 */
	public static String toUnderlineString(String inputString) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < inputString.length() - 1; i++) {
			char c = inputString.charAt(i);
			boolean nextUpperCase = true;
			if (i < inputString.length()) {
				nextUpperCase = Character.isUpperCase(inputString.charAt(i + 1));
			}
			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 将驼峰字段转成属性字符串 (例:branch_no -> branchNo )
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String toCamelCaseString(String inputString) {
		return toCamelCaseString(inputString, false);
	}

	/**
	 * 将驼峰字段转成属性字符串 (例:branch_no -> branchNo )
	 * 
	 * @param inputString
	 *            输入字符串
	 * @param firstCharacterUppercase
	 *            是否首字母大写
	 * @return
	 */
	public static String toCamelCaseString(String inputString, boolean firstCharacterUppercase) {
		if (inputString == null)
			return null;
		StringBuilder sb = new StringBuilder();
		boolean nextUpperCase = false;
		for (int i = 0; i < inputString.length(); i++) {
			char c = inputString.charAt(i);
			switch (c) {
			case '_':
			case '-':
			case '@':
			case '$':
			case '#':
			case ' ':
			case '/':
			case '&':
				if (sb.length() > 0) {
					nextUpperCase = true;
				}
				break;
			default:
				if (nextUpperCase) {
					sb.append(Character.toUpperCase(c));
					nextUpperCase = false;
				} else {
					sb.append(Character.toLowerCase(c));
				}
				break;
			}
		}
		if (firstCharacterUppercase) {
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
		}
		return sb.toString();
	}

	/**
	 * 得到标准字段名称
	 * 
	 * @param inputString
	 *            输入字符串
	 * @return
	 */
	public static String getValidPropertyName(String inputString) {
		String answer;
		if (inputString == null) {
			answer = null;
		} else if (inputString.length() < 2) {
			answer = inputString.toLowerCase(Locale.US);
		} else {
			if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
			} else {
				answer = inputString;
			}
		}
		return answer;
	}

	/**
	 * 将属性转换成标准set方法名字符串
	 * 
	 * @param property
	 * @return
	 */
	public static String getSetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "set");
		return sb.toString();
	}

	/**
	 * 将属性转换成标准get方法名字符串
	 * 
	 * @param property
	 * @return
	 */
	public static String getGetterMethodName(String property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property);
		if (Character.isLowerCase(sb.charAt(0))) {
			if (sb.length() == 1 || !Character.isUpperCase(sb.charAt(1))) {
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			}
		}
		sb.insert(0, "get");
		return sb.toString();
	}

	/**
	 * 对象转Map
	 * 
	 * @param object
	 *            目标对象
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Map toMap(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		return BeanUtil.describe(object);
	}

	/**
	 * 转换为Collection>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static Collection toMapList(Collection collection) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List retList = new ArrayList();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}

	private static void convert(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {

		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean) {
			DynaProperty origDescriptors[] = ((DynaBean) orig).getDynaClass().getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				if (PropertyUtils.isWriteable(dest, name)) {
					Object value = ((DynaBean) orig).get(name);
					try {
						BeanUtils.copyProperty(dest, name, value);
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		} else if (orig instanceof Map) {
			Iterator names = ((Map) orig).keySet().iterator();
			while (names.hasNext()) {
				String name = (String) names.next();
				if (PropertyUtils.isWriteable(dest, name)) {
					Object value = ((Map) orig).get(name);
					try {
						BeanUtils.copyProperty(dest, name, value);
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		} else
		/* if (orig is a standard JavaBean) */
		{
			PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
			for (int i = 0; i < origDescriptors.length; i++) {
				String name = origDescriptors[i].getName();
				// String type =
				// origDescriptors[i].getPropertyType().toString();
				if ("class".equals(name)) {
					continue; // No point in trying to set an object's class
				}
				if (PropertyUtils.isReadable(orig, name) && PropertyUtils.isWriteable(dest, name)) {
					try {
						Object value = PropertyUtils.getSimpleProperty(orig, name);
						BeanUtils.copyProperty(dest, name, value);
					} catch (java.lang.IllegalArgumentException ie) {
						; // Should not happen
					} catch (Exception e) {
						; // Should not happen
					}

				}
			}
		}

	}

	/**
	 * 对象拷贝 数据对象空值不拷贝到目标对象
	 * 
	 * @param dataObject
	 * @param toObject
	 * @throws NoSuchMethodException
	 *             copy
	 */
	public static void copyBeanNotNull2Bean(Object databean, Object tobean) throws Exception {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			// String type = origDescriptors[i].getPropertyType().toString();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(databean, name);
					if (value != null) {
						BeanUtils.copyProperty(tobean, name, value);
					}
				} catch (java.lang.IllegalArgumentException ie) {
					; // Should not happen
				} catch (Exception e) {
					; // Should not happen
				}

			}
		}
	}

	/**
	 * 把orig和dest相同属性的value复制到dest中
	 * 
	 * @param dest
	 * @param orig
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyBean2Bean(Object dest, Object orig) throws Exception {
		convert(dest, orig);
	}

	public static void copyBean2Map(Map map, Object bean) {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < pds.length; i++) {
			PropertyDescriptor pd = pds[i];
			String propname = pd.getName();
			try {
				Object propvalue = PropertyUtils.getSimpleProperty(bean, propname);
				map.put(propname, propvalue);
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 将Map内的key与Bean中属性相同的内容复制到BEAN中
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	/**
	 * 自动转Map key值大写 将Map内的key与Bean中属性相同的内容复制到BEAN中
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean_Nobig(Object bean, Map properties) throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			name = name.toLowerCase();
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	/**
	 * Map内的key与Bean中属性相同的内容复制到BEAN中 对于存在空值的取默认值
	 * 
	 * @param bean
	 *            Object
	 * @param properties
	 *            Map
	 * @param defaultValue
	 *            String
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void copyMap2Bean(Object bean, Map properties, String defaultValue) throws IllegalAccessException, InvocationTargetException {
		// Do nothing unless both arguments have been specified
		if ((bean == null) || (properties == null)) {
			return;
		}
		// Loop through the property name/value pairs to be set
		Iterator names = properties.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			// Identify the property name and value(s) to be assigned
			if (name == null) {
				continue;
			}
			Object value = properties.get(name);
			try {
				Class clazz = PropertyUtils.getPropertyType(bean, name);
				if (null == clazz) {
					continue;
				}
				String className = clazz.getName();
				if (className.equalsIgnoreCase("java.sql.Timestamp")) {
					if (value == null || value.equals("")) {
						continue;
					}
				}
				if (className.equalsIgnoreCase("java.lang.String")) {
					if (value == null) {
						value = defaultValue;
					}
				}
				BeanUtils.setProperty(bean, name, value);
			} catch (NoSuchMethodException e) {
				continue;
			}
		}
	}

	public static List fetchElementPropertyToList(Collection collection, String propertyName) {
		List list = new ArrayList();
		try {
			for (Iterator localIterator = collection.iterator(); localIterator.hasNext();) {
				Object obj = localIterator.next();
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
		}

		return list;
	}

	public static String fetchElementPropertyToString(Collection collection, String propertyName, String separator) {
		List list = fetchElementPropertyToList(collection, propertyName);
		// return StringUtils.join(list.toArray(), separator);//***
		return "";
	}

	/**
	 * 处理object以下属性中,存在null值
	 * 
	 * @param obj
	 * @return
	 */
	public static Object parseNull(Object obj) {
		try {
			PropertyUtilsBean b = new PropertyUtilsBean();
			Map map = b.describe(obj);
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				if (map.get(key) == null) {
					if (b.getPropertyType(obj, key) == String.class)
						b.setProperty(obj, key.toString(), "");
					else if (b.getPropertyType(obj, key) == Integer.class || b.getPropertyType(obj, key) == int.class)
						b.setProperty(obj, key.toString(), 0);
					else if (b.getPropertyType(obj, key) == Long.class || b.getPropertyType(obj, key) == long.class)
						b.setProperty(obj, key.toString(), 0l);
					else if (b.getPropertyType(obj, key) == Double.class || b.getPropertyType(obj, key) == double.class)
						b.setProperty(obj, key.toString(), 0.00);
					else
						b.setProperty(obj, key.toString(), "");
				}
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * Beanutils只可以默认转换了最基本的类型 如果希望设置其他类型，可以自己转换 或是注册转换器 如果设置的没有某个属性，则会忽略
	 * 
	 * @throws Exception
	 */
	public void Test_setValue() throws Exception {
		// User29 u = new User29();
		// String dd = "2009-09-23";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// Date ddd = sdf.parse(dd);
		// BeanUtils.setProperty(u, "name", "Jack");
		// BeanUtils.setProperty(u, "age", "90");
		// BeanUtils.setProperty(u, "addr", "中国");
		// BeanUtils.setProperty(u, "birth", ddd);
		// System.err.println(u);
	}

 

	public static void main(String[] args) throws IllegalAccessException {
		System.out.println(BeanUtil.toUnderlineString("ISOCertifiedStaff"));
		System.out.println(BeanUtil.getValidPropertyName("CertifiedStaff"));
		System.out.println(BeanUtil.getSetterMethodName("userID"));
		System.out.println(BeanUtil.getGetterMethodName("userID"));
		System.out.println(BeanUtil.toCamelCaseString("iso_certified_staff", true));
		System.out.println(BeanUtil.getValidPropertyName("certified_staff"));
		System.out.println(BeanUtil.toCamelCaseString("site_Id"));
		BeanUtil p = new BeanUtil();
		System.out.println("Web Class Path = " + p.getWebClassesPath());
		System.out.println("WEB-INF Path = " + p.getWebInfPath());
		System.out.println("WebRoot Path = " + p.getWebRoot());
	}

	@Test
	public  void Test_testBeanUtils() throws Exception {
		// // =======getProperty
		// SampleBean bean1 = new SampleBean();
		// bean1.setName("rensanning");
		// bean1.setAge(31);
		//
		// String name = BeanUtils.getProperty(bean1, "name");
		// String age = BeanUtils.getProperty(bean1, "age");
		//
		// System.out.println(name);
		// System.out.println(age);
		//
		// // =======setProperty
		// SampleBean bean2 = new SampleBean();
		// BeanUtils.setProperty(bean2, "name", "rensanning");
		// BeanUtils.setProperty(bean2, "age", 31);
		//
		// System.out.println(bean2.getName());
		// System.out.println(bean2.getAge());
		//
		// // =======cloneBean
		// SampleBean bean3 = new SampleBean();
		// bean3.setName("rensanning");
		// bean3.setAge(31);
		//
		// SampleBean clone = (SampleBean) BeanUtils.cloneBean(bean3);
		//
		// System.out.println(clone.getName());
		// System.out.println(clone.getAge());
		//
		// // =======describe
		// SampleBean bean4 = new SampleBean();
		// bean4.setName("rensanning");
		// bean4.setAge(31);
		//
		// @SuppressWarnings("unchecked")
		// Map<String, String> map4 = BeanUtils.describe(bean4);
		//
		// System.out.println(map4.get("name"));
		// System.out.println(map4.get("age"));
		//
		// // =======populate
		// SampleBean bean5 = new SampleBean();
		//
		// Map<String, String> map5 = new HashMap<String, String>();
		// map5.put("name", "rensanning");
		// map5.put("age", "31");
		//
		// BeanUtils.populate(bean5, map5);
		//
		// System.out.println(bean5.getName());
		// System.out.println(bean5.getAge());
		//
		// // =======getArrayProperty
		// SampleBean bean6 = new SampleBean();
		// bean6.setArray(new String[] { "a", "b", "c" });
		// List<String> list0 = new ArrayList<String>();
		// list0.add("d");
		// list0.add("e");
		// list0.add("f");
		// bean6.setList(list0);
		//
		// String[] array = BeanUtils.getArrayProperty(bean6, "array");
		//
		// System.out.println(array.length);// 3
		// System.out.println(array[0]);// "a"
		// System.out.println(array[1]);// "b"
		// System.out.println(array[2]);// "c"
		//
		// String[] list = BeanUtils.getArrayProperty(bean6, "list");
		// System.out.println(list.length);// 3
		// System.out.println(list[0]);// "d"
		// System.out.println(list[1]);// "e"
		// System.out.println(list[2]);// "f"
		//
		// System.out.println(BeanUtils.getProperty(bean6, "array[1]"));// "b"
		// System.out.println(BeanUtils.getIndexedProperty(bean6, "array",
		// 2));// "c"
		//
		// // =======getMappedProperty
		// SampleBean bean7 = new SampleBean();
		// Map<String, String> map = new HashMap<String, String>();
		// map.put("key1", "value1");
		// map.put("key2", "value2");
		// bean7.setMap(map);
		//
		// String value1 = BeanUtils.getMappedProperty(bean7, "map", "key1");
		// System.out.println(value1);// "value1"
		//
		// String value2 = BeanUtils.getMappedProperty(bean7, "map", "key2");
		// System.out.println(value2);// "value2"
		//
		// System.out.println(BeanUtils.getProperty(bean7, "map.key1"));//
		// "value1"
		// System.out.println(BeanUtils.getProperty(bean7, "map.key2"));//
		// "value2"
		//
		// // =======getNestedProperty
		// SampleBean bean = new SampleBean();
		// NestedBean nestedBean = new NestedBean();
		// nestedBean.setNestedProperty("xxx");
		// bean.setNestedBean(nestedBean);
		//
		// String value = BeanUtils.getNestedProperty(bean,
		// "nestedBean.nestedProperty");
		// System.out.println(value);// "xxx"
		//
		// System.out.println(BeanUtils.getProperty(bean,
		// "nestedBean.nestedProperty"));// "xxx"
		//
		// // =======testURLConversion
		// SampleBean bean8 = new SampleBean();
		//
		// BeanUtils.setProperty(bean8, "url", "http://www.google.com/");
		//
		// URL url = bean8.getUrl();
		// System.out.println(url.getProtocol());// "http"
		// System.out.println(url.getHost());// "www.google.com"
		// System.out.println(url.getPath());// "/"
		//
		// // =======testDateConversion
		// SampleBean bean9 = new SampleBean();
		//
		// DateConverter converter = new DateConverter();
		// converter.setPattern("yyyy/MM/dd HH:mm:ss");
		//
		// ConvertUtils.register(converter, Date.class);
		// ConvertUtils.register(converter, String.class);
		//
		// BeanUtils.setProperty(bean9, "date", "2010/12/19 23:40:00");
		//
		// String value9 = BeanUtils.getProperty(bean9, "date");
		// System.out.println(value9);// "2010/12/19 23:40:00"
	}

	// *****************************************************************************************************************
	// *****************************************************************************************************************
	// @Test
	/*
	 * public void test1() throws IllegalAccessException,
	 * InvocationTargetException { ConvertUtils.register(new Converter() {//
	 * 注册自定义转换器； public Object convert(Class type, Object value) { if (value ==
	 * null) return null; if (!(value instanceof String)) { throw new
	 * ConversionException("这里只支持String类型！"); } if (((String)
	 * value).trim().equals("")) {// trim:返回字符串的副本，忽略前导空白和尾部空白。 return null; }
	 * SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");// 设置日期格式 try {
	 * return df.parse((String) value); // 将字符串转为Date对象； } catch (ParseException
	 * e) { throw new RuntimeException(e); } } }, java.util.Date.class); bean bb
	 * = new bean(); BeanUtils.setProperty(bb, "a", "faffafdas");
	 * BeanUtils.setProperty(bb, "b", 123); BeanUtils.setProperty(bb, "c",
	 * "1999-02-12"); System.out.println(bb.getA());
	 * System.out.println(bb.getB()); System.out.println(bb.getC()); }
	 */

	// @Test
	/*
	 * public void test2() throws IllegalAccessException,
	 * InvocationTargetException { // map中元素必须和bean中的属性对应： Map<Object, String>
	 * map = new HashMap<Object, String>(); map.put("a", "dfa"); map.put("b",
	 * "4568"); map.put("c", "2121-11-23"); ConvertUtils.register(new
	 * DateLocaleConverter(), java.util.Date.class); bean b = new bean();
	 * BeanUtils.populate(b, map); System.out.println(b.getA());
	 * System.out.println(b.getB()); System.out.println(b.getC()); }
	 */

	// @Test
	/*
	 * public void test11() throws Exception { Person p = new Person();
	 * BeanUtils.setProperty(p, "age", 456); System.out.println(p.getAge());//
	 * 456 }
	 */

	// @Test
	/*
	 * public void test12() throws Exception { String name = "aaaa"; String age
	 * = "123"; String password = "pw"; Person p = new Person(); //
	 * 支持8种基本类型自动转换，其他复杂类型的 转换，需要 编写 并且注册类型转换器，最常见的日起类型的 转换器。
	 * BeanUtils.setProperty(p, "name", name); BeanUtils.setProperty(p, "age",
	 * age); BeanUtils.setProperty(p, "password", password);
	 * System.out.println(p.getName());// aaaa System.out.println(p.getAge());//
	 * 123 // System.out.println(p.getPassword());//pw }
	 */

	// @Test
	public void Test_test13() throws Exception {
		String birthday = "1983-12-1";
		// 为了让日期赋值到bean的birthday属性上，需要在 beanUtils中 注册一个日期转换器，以便在需要转换时自动调用。可查
		// BeanUtil 的文档。
		// ConvertUtils.register(converter, clazz);
		ConvertUtils.register(new Converter() { // 下面是自定义的类型转换器。
			public Object convert(Class type, Object value) {
				if (value == null)
					return null;
				if (!(value instanceof String)) {
					throw new ConversionException("只支持String类型的转换");// 可查文档，此异常父类是RunnableTimeException
				}
				String str = (String) value;
				if (str.trim().equals(""))
					return null;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				try {
					return df.parse(str); // 此语句将会抛出异常，但是由于
					// 子类不能抛出比父类更多的异常，所以只能catch
				} catch (ParseException e) {
					throw new RuntimeException(e); // 抛出运行时异常并停止程序的执行，以便通知上层异常信息，
					// 并建议其修改代码，以期提高代码的严谨性。
				}
			}
		}, Date.class);
		// Person p = new Person();
		// BeanUtils.setProperty(p, "birthday", birthday);// 因上面注册了“字符串--日起类型”
		// 的类型转换器，故此就不会报错异常了。
		// System.out.println(p.getBirthday());//pw
		// System.out.println("___" + BeanUtils.getProperty(p, "birthday"));
		/*
		 * 关于转换器：BeanUtils已经为 Converter接口 编写很多的转换器， 可查阅文档中All Known Implementing
		 * Classes:中所提示的各种框架自带转换器。
		 * 注：其中DateLocalConverter转换器，是针对中文样式的，但是里面有Bug：当输入的字符串类型
		 * 的日期参数值为" "时，就会抛出异常， 可考虑在编码时避免传入" " 值，就可以直接使用该转换器 Demo：
		 * ConvertUtils.register(new DateLocalConverter(), Date.class); 仅需要
		 * 这一句代码 就可以实现中文样式的 “字符串--日期” 的类型转换器：
		 */
	}

	// @Test
	public void Test_test15() throws Exception {
		Map map = new HashMap();
		map.put("name", "aaa");
		map.put("password", "123");
		map.put("brithday", "1980-09-09");
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
		// Person p = new Person();
		// 用map集合填充bean属性,map关键字和bean属性要一致
		// BeanUtil.populate(p, map);
	}

	/**
	 * 通过反射获取属性并赋值
	 * 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	// @Test
	/*
	 * public void invoke1() throws IllegalArgumentException,
	 * IllegalAccessException, InvocationTargetException,
	 * InstantiationException, IntrospectionException { Person person =
	 * Person.class.newInstance(); BeanInfo beaninfo =
	 * Introspector.getBeanInfo(Person.class); PropertyDescriptor[]
	 * porpertydescriptors = beaninfo .getPropertyDescriptors(); for
	 * (PropertyDescriptor pd : porpertydescriptors) {
	 * System.err.println(pd.getName()); // 打印出所有的属性 //
	 * 这里是需要判断的，咱们已经打印出所有属性的名字，然后想修改哪个属性在来判断就很方便了 // 如果属性的名字和name一样 if
	 * (pd.getName().equals("name")) { // 找到我们需要的属性，然后通过反射获取该属性的get和set方法 Method
	 * setMethod = pd.getWriteMethod();// 获得该属性的set方法 Method getMethod =
	 * pd.getReadMethod(); // 获得该属性的get方法 // 然后咱们调用获得的set方法来设置属性的value
	 * setMethod.invoke(person, "温家宝"); // 设置好后咱们来调用属性的get方法来看看是否设置成功
	 * System.out.println(getMethod.invoke(person));//
	 * 属性的get方法一都没有参数，所以是null，也可以不写 break; } } }
	 */

	/**
	 * 设置JavaBean中的某个属性
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	// @Test
	/*
	 * public void invoke2() throws InstantiationException,
	 * IllegalAccessException, IntrospectionException, IllegalArgumentException,
	 * InvocationTargetException { // 首先得到Person类的一个对象 Person person =
	 * Person.class.newInstance(); // 然后得到咱们需要设置的某个属性(比如age)的属性描述器 //
	 * 参数分别为需要设置哪个类的哪个属性 PropertyDescriptor pd = new PropertyDescriptor("age",
	 * Person.class); // 得到该属性的描述器后，咱们就可以通过反射来得到该属性的get和set方法了 Method setMethod
	 * = pd.getWriteMethod();// 获得该属性的set方法 Method getMethod =
	 * pd.getReadMethod(); // 获得该属性的get方法 setMethod.invoke(person, 56);
	 * System.out.println(getMethod.invoke(person)); }
	 */

	// @Test
	public void Test_invoke3() throws IllegalAccessException, InvocationTargetException {
		// 先创建出Person对象
		// Person person = new Person();
		// 设置name属性的值
		// 参数如下：为哪个对象，哪个属性名，赋什么属性值
		// BeanUtils.copyProperty(person, "name", "胡锦涛");
		// 打印一下，看name的属性值是否设置成功
		// System.out.println(person.getName());
	}

	// @Test
	// 此单元是用Map集合的方式给属性赋值
	public void Test_invoke4() throws IllegalAccessException, InvocationTargetException {
		// 先创建出Person对象
		// Person person = new Person();
		// 创建Map集合
		Map map = new HashMap();
		// 向集合中添加元素,这里添加的就是属性名和属性值
		map.put("name", "李嘉诚");
		map.put("age", "65");
		// 调用populate方法来设置value，参数分别是为哪个对象设置value，存放键值的集合
		// BeanUtil.populate(person, map);
		// 这样就将属性值都设置好了，打印一下看是否成功设置
		// System.out.println(person.getAge());
		// System.out.println(person.getName());
		// 上面的代码其实有个小问题，age属性其实是int类型，但Map集合中存放的都是String类型的
		// 实际上运行没有错误，但我也不知道存在一些什么隐患，以后遇到在补充吧
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
			log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		// 返回表示此类型实际类型参数的 Type 对象的数组，获得超类的泛型参数的实际类型。。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
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

	public static void setProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.setProperty(bean, name, value);
	}

	/**
	 * 把请求参数封装到JavaBean中，前提表单的字段名和JavaBean属性保持一致
	 * 
	 * @param request
	 * @param clazz
	 *            目标类型
	 * @return
	 */
	public static <T> T fillBean(HttpServletRequest request, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			// 注册日期类型转换器:
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			BeanUtils.populate(bean, request.getParameterMap());
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static Object convertToDate(Class type, Object value, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if (value instanceof String) {
			try {
				if (StringUtil.isEmpty(value.toString())) {
					return null;
				}
				java.util.Date date = sdf.parse(String.valueOf(value));
				if (type.equals(Timestamp.class)) {
					return new Timestamp(date.getTime());
				}
				return date;
			} catch (Exception pe) {
				return null;
			}
		} else if (value instanceof Date) {
			return value;
		}
		throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
	}

	protected static Object convertToString(Class type, Object value) {
		if (value instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (value instanceof Timestamp) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			try {
				return sdf.format(value);
			} catch (Exception e) {
				throw new ConversionException("日期转换为字符串时出错！");
			}
		} else {
			return value.toString();
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 重写Converter接囗的convert方法来自定义转换器；
			public Object convert(Class type, Object value) {
				if (value == null)
					return null;
				String str = (String) value;
				if (str.trim().equals("")) {
					return null;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				try {
					return df.parse(str); // 将字符串转为Date对象；
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}, Date.class);
		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean4(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			public Object convert(Class type, Object value) {
				if (value == null)
					return null;
				if (!(value instanceof String)) {
					// throw new ConversionException("这里只支持String类型！");
				}
				if (((String) value).trim().equals("")) {// trim:返回字符串的副本，忽略前导空白和尾部空白。
					return null;
				}
				SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");// 设置日期格式
				// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss");// 设置日期格式
				try {
					return df.parse((String) value); // 将字符串转为Date对象；
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);

		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void copyBean3(Object src, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				} else {
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);
		// BeanUtils.populate(b, map);

		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	public static void paramToBean(HttpServletRequest request, Object dest) {
		Object src = getAllParameters(request);
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			@SuppressWarnings("rawtypes")
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				} else {
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);
		// BeanUtils.populate(b, map);

		try {
			BeanUtils.copyProperties(dest, src);// 复制所有两个bean都有的属性；
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 复制所有属性值从originBean到destinationBean中；
	@SuppressWarnings("rawtypes")
	public static void MapToBean(Map map, Object dest) {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				} else if (type == Timestamp.class) {
					return convertToDate(type, value, "yyyy-MM-dd HH:mm:ss");
				} else if (type == Date.class) {
					return convertToDate(type, value, "yyyy-MM-dd");
				} else if (type == String.class) {
					return convertToString(type, value);
				} else {
					throw new ConversionException("不能转换 " + value.getClass().getName() + " 为 " + type.getName());
				}
			}
		}, java.util.Date.class);
		// ConvertUtils.register(new DateLocaleConverter(),
		// java.util.Date.class);

		try {
			BeanUtils.populate(dest, map);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T populate(T t, Map<String, ? extends Object> param) {
		try {
			org.apache.commons.beanutils.BeanUtils.populate(t, param);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}

	public static <T> T populate2(Class<T> cls, Map<String, ? extends Object> param) {
		T t = null;
		try {
			t = populate(cls.newInstance(), param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	// wujun

	private static SimpleDateFormat sdf = new SimpleDateFormat("mmssSSS");

	/**
	 * 生成订单编号
	 */
	public static String getNo(Integer hashCode) {
		Date date = new Date();
		String string = sdf.format(date);
		return hashCode + string;
	}

	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}

	public static String getArea(String strip) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;
		String strRtn = null;
		try {
			// MyJdbc myjdbc = new MyJdbc();
			// conn = myjdbc.getConn();
			sql = "select * from fullip where startip<='" + strip + "' and endip>='" + strip + "'";
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				strRtn = rs.getString("country");
			} else {
				strRtn = "δȷ��";
			}
			rs.close();
			rs = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
					pstmt = null;
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
					conn = null;
				} catch (Exception e) {
				}
		}
		return strRtn;
	}

	/**
	 * ��ip��ַ��ʽ��Ϊ��000.000.000.000
	 * 
	 * @param ip
	 * @return ���ع��ip
	 */
	public static String strfullip(String ip) {
		StringBuffer buff = new StringBuffer();
		buff.append("");
		String strzero = "000";
		int ilen = 0;
		if (ip != null) {
			String[] arrip = ip.split("\\.");
			if (arrip.length == 4) {
				for (int i = 0; i < 4; i++) {
					if (i == 0) {
						ilen = arrip[i].length();
						if (ilen < 3) {
							buff.append(strzero.substring(0, 3 - ilen)).append(arrip[i]);
						} else {
							buff.append(arrip[i]);
						}
					} else {
						ilen = arrip[i].length();
						if (ilen < 3) {
							buff.append(".").append(strzero.substring(0, 3 - ilen)).append(arrip[i]);
						} else {
							buff.append(".").append(arrip[i]);
						}
					}
				}
			}
		}
		return buff.toString();
	}

	/**
	 * @param args
	 */
	@Test
	public void main() {
		String strip = "202.108.33.32";
		System.out.println(BeanUtil.strfullip(strip));
		System.out.println(System.currentTimeMillis());
		System.out.println("ip" + strip + "���ڵ���" + BeanUtil.getArea(BeanUtil.strfullip(strip)));
		System.out.println(System.currentTimeMillis());
	}

	/**
	 * ɾ���ﳵ
	 */
	public static void deleteBuyCart(HttpServletRequest request) {
		request.getSession().removeAttribute("buyCart");
	}

	/***
	 * ��ȡURI��·��,��·��Ϊhttp://www.babasport.com/action/post.htm?method=add,
	 * �õ���ֵΪ"/action/post.htm"
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * ��ȡ��������·��(������·�����������)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return getRequestURI(request) + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}

	/**
	 * ���cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie�����
	 * @param value
	 *            cookie��ֵ
	 * @param maxAge
	 *            cookie��ŵ�ʱ��(����Ϊ��λ,����������,��3*24*60*60;
	 *            ���ֵΪ0,cookie����������رն����)
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * ��ȡcookie��ֵ
	 * 
	 * @param request
	 * @param name
	 *            cookie�����
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = BeanUtil.readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	/**
	 * ȥ��html����
	 * 
	 * @param inputString
	 * @return
	 */
	public static String HtmltoText(String inputString) {
		String htmlStr = inputString; // ��html��ǩ���ַ�
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // ����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // ����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // ����script��ǩ

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // ����style��ǩ

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // ����html��ǩ

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // ���˿ո�

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// �����ı��ַ�
	}

	public String getWebClassesPath() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;

	}

	public String getWebInfPath() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF") + 8);
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public String getWebRoot() throws IllegalAccessException {
		String path = getWebClassesPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		} else {
			throw new IllegalAccessException("路径获取错误");
		}
		return path;
	}

	public static String getDateStr(String pt) {
		if (pt == null || pt.trim().length() == 0) {
			pt = "yyyy-MM-dd";
		}

		SimpleDateFormat fm = new SimpleDateFormat();
		fm.applyPattern(pt);
		return fm.format(new Date());
	}

	public static String getDateStr(Object dateObj, String pt) {
		Date date = null;
		if (dateObj instanceof Date) {
			if (dateObj == null) {
				return "";
			}
			date = (Date) dateObj;
		} else {
			if (dateObj == null || dateObj.toString().length() == 0) {
				return "";
			}

			java.sql.Timestamp sqlDate = java.sql.Timestamp.valueOf(dateObj.toString());
			date = new Date(sqlDate.getTime());
		}

		if (pt == null || pt.trim().length() == 0) {
			pt = "yyyy-MM-dd";
		}

		SimpleDateFormat fm = new SimpleDateFormat();
		fm.applyPattern(pt);
		return fm.format(date);
	}

	public static Date getDate(String str, String pt) throws ParseException {
		if (str == null || str.trim().length() == 0) {
			return null;
		}

		if (pt == null || pt.trim().length() == 0) {
			pt = "yyyy-MM-dd";
		}

		SimpleDateFormat fm = new SimpleDateFormat();
		fm.applyPattern(pt);
		return fm.parse(str);
	}

	public static java.sql.Timestamp getSqlDate() {
		long dateTime = new Date().getTime();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(dateTime);
		return sqlDate;
	}

	public static java.sql.Timestamp getSqlDate(String timeValue) {
		java.sql.Timestamp sqlDate = null;
		Long dateTime = BeanUtil.getDateTime(timeValue);
		if (dateTime != null) {
			sqlDate = new java.sql.Timestamp(dateTime.longValue());
		}
		return sqlDate;
	}

	public static Long getDateTime(String timeValue) {
		Long dateTime = null;

		if (timeValue != null && timeValue.trim().length() > 0) {
			timeValue = rightPadTo(timeValue, "1900-01-01 00:00:00");
			timeValue = timeValue.replace("/", "-");

			try {
				Date date = BeanUtil.getDate(timeValue, "yyyy-MM-dd HH:mm:ss");
				dateTime = new Long(date.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return dateTime;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		Map<String, String> parmsMap = new HashMap<String, String>();

		Map<String, String[]> properties = request.getParameterMap();
		Object obj = "";
		String value = "";
		String[] values = null;

		for (String key : properties.keySet()) {
			obj = properties.get(key);
			if (null == obj) {
				value = "";
			} else if (obj instanceof String[]) {
				value = "";
				values = (String[]) obj;
				for (int i = 0; i < values.length; i++) {
					value += "," + values[i];
				}
				value = value.length() > 0 ? value.substring(1) : value;
			} else {
				value = obj.toString();
			}

			parmsMap.put(key, value);
		}

		return parmsMap;
	}

	public static String changeUTF(String str) {

		String newStr = null;
		try {
			newStr = new String(str.getBytes("iso8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}

	public static String rightPadTo(String src, String dec) {
		String retStr = src;
		int len = src.length();
		if (dec.length() - len > 0) {
			retStr += dec.substring(len);
		}
		return retStr;
	}

	/***********************************************************************************************/
	/***********************************************************************************************/

	public static <T> T requestToBean(HttpServletRequest request, Class<T> beanClass) {
		try {
			T bean = beanClass.newInstance();
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {// 测试此枚举是否包含更多的元素。
				String name = (String) e.nextElement();// 返回此枚举的下一个元素。
				String value = request.getParameter(name);// 获取request中对应名称的值；
				// 将指定的名称和值存进指定的javaBean中对应的属性；
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 生成全球唯一的ID；
	public static String gnerateID() {
		return UUID.randomUUID().toString();// UUID表示通知唯一标识码
	}

	// ************************************************************************************
	// ************************************************************************************

	private static String webAppRoot = null;

	public static String getWebAppRoot() {
		return webAppRoot;
	}

	public static void setWebAppRoot(String pWebAppRoot) {
		webAppRoot = pWebAppRoot;
	}

	// ************************************************************************************
	// ************************************************************************************
	public static String removeHightlight(String content) {
		if (StringUtil.isEmpty(content))
			return content;
		int before = content.indexOf('<');
		int behind = content.indexOf('>');
		if (before != -1 || behind != -1) {
			behind += 1;
			content = content.substring(0, before).trim() + content.substring(behind, content.length()).trim();
			content = removeHightlight(content);
		}
		return content;
	}

	// ************************************************************************************
	// ************************************************************************************
	private static String numberFilePath;// number.txt的真实路径
	static {
		URL url = BeanUtil.class.getClassLoader().getResource("config.properties");
		numberFilePath = url.getPath();// 不要把Tomcat装在有空格或中文的目录下
	}

	// 生成一个唯一的编号:yyyyMMdd00000001 20150210 00000001
	public synchronized static String genApplyNumber() {

		try {
			// 读取number.txt文件，获得当前的编号
			InputStream in = new FileInputStream(numberFilePath);
			byte data[] = new byte[in.available()];
			in.read(data);
			in.close();
			String count = new String(data);// 1
			// 按照约定组织成申请编号，返回
			// ----------------------
			Date now = new Date();
			String prefix = new SimpleDateFormat("yyyyMMdd").format(now);// 20150210
			// ---------------------
			StringBuffer sb = new StringBuffer(prefix);
			for (int i = 0; i < (8 - count.length()); i++) {
				sb.append("0");
			}
			sb.append(count);
			// 加1后，写入number.txt文件
			OutputStream out = new FileOutputStream(numberFilePath);
			out.write((Integer.parseInt(count) + 1 + "").getBytes());
			out.close();

			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main123(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(genApplyNumber());
		}
	}

	// ************************************************************************************//
	// ************************************************************************************//
	/***********************************************************************************************/
	/***********************************************************************************************/

	@Deprecated
	public static <T> T copyToBean_old(HttpServletRequest request, Class<T> clazz) {
		try {
			// ��������
			T t = clazz.newInstance();

			// ��ȡ���еı?Ԫ�ص����
			Enumeration<String> enums = request.getParameterNames();
			// ����
			while (enums.hasMoreElements()) {
				// ��ȡ�?Ԫ�ص����:<input type="password" name="pwd"/>
				String name = enums.nextElement(); // pwd
				// ��ȡ��ƶ�Ӧ��ֵ
				String value = request.getParameter(name);
				// ��ָ��������ƶ�Ӧ��ֵ���п���
				BeanUtils.copyProperty(t, name, value);
			}

			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ����������ݵķ�װ
	 */
	public static <T> T copyToBean(HttpServletRequest request, Class<T> clazz) {
		try {
			// ��ע����������ת������
			// ��������
			T t = clazz.newInstance();
			BeanUtils.populate(t, request.getParameterMap());
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/***********************************************************************************************/
	/***********************************************************************************************/

	public static <T> T requestToBean22(HttpServletRequest request, Class<T> beanClass) {
		try {
			T bean = beanClass.newInstance();
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {// 测试此枚举是否包含更多的元素。
				String name = (String) e.nextElement();// 返回此枚举的下一个元素。
				String value = request.getParameter(name);// 获取request中对应名称的值；
				// 将指定的名称和值存进指定的javaBean中对应的属性；
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	// 生成全球唯一的ID；
	public static String gnerateID22() {
		return UUID.randomUUID().toString();// UUID表示通知唯一标识码
	}

	/***********************************************************************************************/
	/***********************************************************************************************/

	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取Unix网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				/**
				 * 寻找标示字符串[hwaddr]
				 */
				index = line.toLowerCase().indexOf("hwaddr");
				/**
				 * 找到了
				 */
				if (index != -1) {
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取Linux网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				/**
				 * 找到了
				 */
				if (index != -1) {
					/**
					 * 取出mac地址并去除2边空格
					 */
					mac = line.substring(index + 4).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			/**
			 * windows下的命令，显示信息中包含有mac地址信息
			 */
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				/**
				 * 寻找标示字符串[physical address]
				 */
				index = line.toLowerCase().indexOf("physical address");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						/**
						 * 取出mac地址并去除2边空格
						 */
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	// WindowsCmd ="cmd.exe /c echo %NUMBER_OF_PROCESSORS%";//windows的特殊
	// SolarisCmd = {"/bin/sh", "-c", "/usr/sbin/psrinfo | wc -l"};
	// AIXCmd = {"/bin/sh", "-c", "/usr/sbin/lsdev -Cc processor | wc -l"};
	// HPUXCmd = {"/bin/sh", "-c", "echo \"map\" | /usr/sbin/cstm | grep CPU |
	// wc -l "};
	// LinuxCmd = {"/bin/sh", "-c", "cat /proc/cpuinfo | grep ^process | wc
	// -l"};

	/**
	 * 测试用的main方法.
	 * 
	 * @param argc
	 *            运行参数.
	 */
	public static void main1(String[] argc) {
		String os = getOSName();
		System.out.println(os);
		if (os.startsWith("windows")) {
			String mac = getWindowsMACAddress();
			System.out.println("本地是windows:" + mac);
		} else if (os.startsWith("linux")) {
			String mac = getLinuxMACAddress();
			System.out.println("本地是Linux系统,MAC地址是:" + mac);
		} else {
			String mac = getUnixMACAddress();
			System.out.println("本地是Unix系统 MAC地址是:" + mac);
		}
	}

	/***********************************************************************************************/
	/***********************************************************************************************/

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr1(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf("0:") != -1) {
			ip = "本地";
		}
		return ip;
	}

	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr2(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}

	/***********************************************************************************************/
	/***********************************************************************************************/
	// 根据名称查找指定的cookie
	public static Cookie findCookieByName(Cookie[] cs, String name) {
		if (cs == null || cs.length == 0) {
			return null;
		}

		for (Cookie c : cs) {
			if (c.getName().equals(name)) {
				return c;
			}
		}

		return null;
	}

	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/

	/**
	 * 获得请求路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length());// 去掉项目路径
		return requestPath;
	}

	public static Map getParams2(HttpServletRequest request) {
		Map params = new HashMap();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) {
			String pairs[] = Pattern.compile("&").split(queryString);
			for (int i = 0; i < pairs.length; i++) {
				String p = pairs[i];
				int idx = p.indexOf('=');
				params.put(p.substring(0, idx), URLDecoder.decode(p.substring(idx + 1)));
			}

		}
		return params;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getAllParameters2(HttpServletRequest request) {
		Map bufferMap = Collections.synchronizedMap(new HashMap());
		try {
			for (Enumeration em = request.getParameterNames(); em.hasMoreElements();) {
				String name = (String) (String) em.nextElement();
				String values[] = request.getParameterValues(name);
				String temp[] = new String[values.length];
				if (values.length > 1) {
					for (int i = 0; i < values.length; i++)
						temp[i] = values[i];

					bufferMap.put(name, temp);
				} else {
					bufferMap.put(name, values[0]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferMap;
	}

	public static String CharSetConvert2(String s, String charSetName, String defaultCharSetName) {
		String newString = null;
		try {
			newString = new String(s.getBytes(charSetName), defaultCharSetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NullPointerException nulle) {
			nulle.printStackTrace();
		}
		return newString;
	}
	// ******************************************************************
	// ******************************************************************

	// ******************************************************************
	// ******************************************************************
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/

	// -- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	// -- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// -- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified
	 *            内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag
	 *            内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings("unchecked")
	public static Map getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {// NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************************************************************************************/

	public static void goTo(HttpServletRequest request, HttpServletResponse response, Object uri) throws ServletException, IOException {
		if (uri instanceof RequestDispatcher) {
			((RequestDispatcher) uri).forward(request, response);
		} else if (uri instanceof String) {
			response.sendRedirect(request.getContextPath() + uri);
		}
	}

	public static final String URL_FORM_ENCODED = "application/x-www-form-urlencoded";
	public static final String PUT = "PUT";
	public static final String POST = "POST";

	public static Map getParams(HttpServletRequest request) {
		Map params = new HashMap();
		String queryString = request.getQueryString();
		if (queryString != null && queryString.length() > 0) {
			String pairs[] = Pattern.compile("&").split(queryString);
			for (int i = 0; i < pairs.length; i++) {
				String p = pairs[i];
				int idx = p.indexOf('=');
				params.put(p.substring(0, idx), URLDecoder.decode(p.substring(idx + 1)));
			}

		}
		return params;
	}

	@SuppressWarnings("unchecked")
	public static Map getAllParameters(HttpServletRequest request) {
		Map bufferMap = Collections.synchronizedMap(new HashMap());
		try {
			for (Enumeration em = request.getParameterNames(); em.hasMoreElements();) {
				String name = (String) (String) em.nextElement();
				String values[] = request.getParameterValues(name);
				String temp[] = new String[values.length];
				if (values.length > 1) {
					for (int i = 0; i < values.length; i++)
						temp[i] = values[i];

					bufferMap.put(name, temp);
				} else {
					bufferMap.put(name, values[0]);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferMap;
	}

	public static String CharSetConvert(String s, String charSetName, String defaultCharSetName) {
		String newString = null;
		try {
			newString = new String(s.getBytes(charSetName), defaultCharSetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NullPointerException nulle) {
			nulle.printStackTrace();
		}
		return newString;
	}

	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param param
	 *            �������
	 * @return URL ������Զ����Դ����Ӧ���
	 */
	public static String sendGet(String url, HashMap<String, String> params) {
		String result = "";
		BufferedReader in = null;
		try {
			/** ��װ���� **/
			String param = parseParams(params);
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			/** �򿪺�URL֮������� **/
			URLConnection connection = realUrl.openConnection();
			/** ����ͨ�õ��������� **/
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			/** ����ʵ�ʵ����� **/
			connection.connect();
			/** ���� BufferedReader����������ȡURL����Ӧ **/
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		} finally {/** ʹ��finally�����ر������� **/
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��ָ�� URL ����POST����������
	 * 
	 * @param url
	 *            ��������� URL
	 * @param param
	 *            �������
	 * @return ������Զ����Դ����Ӧ���
	 */
	public static String sendPost(String url, HashMap<String, String> params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			/** �򿪺�URL֮������� **/
			URLConnection conn = realUrl.openConnection();
			/** ����ͨ�õ��������� **/
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			/** ����POST������������������� **/
			conn.setDoOutput(true);
			conn.setDoInput(true);
			/** ��ȡURLConnection�����Ӧ������� **/
			out = new PrintWriter(conn.getOutputStream());
			/** ����������� **/
			String param = parseParams(params);
			out.print(param);
			/** flush������Ļ��� **/
			out.flush();
			/** ����BufferedReader����������ȡURL����Ӧ **/
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		} finally { /** ʹ��finally�����ر�������������� **/
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��HashMap������װ���ַ���
	 * 
	 * @param map
	 * @return
	 */
	private static String parseParams(HashMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (Entry<String, String> e : map.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post方式请求服务器(https协议)
	 * 
	 * @param url
	 *            请求地址
	 * @param content
	 *            参数
	 * @param charset
	 *            编码
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static String post(String url, String content, String charset) throws NoSuchAlgorithmException, KeyManagementException, IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setRequestProperty("ContentType", "text/xml;charset=utf-8");
		conn.setRequestProperty("charset", "utf-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();

			String result = "";

			result = new String(outStream.toByteArray(), "utf-8");
			return result;
		}
		return null;
	}

	public static void test(String[] args) throws Exception {
		String url = "https://61.135.144.37:8443/userbinding/api/createpush";
		// String url = "https://113.106.93.82:9401/api/stockapi_notice";
		String content = "test";
		String charset = "utf-8";

		String jsonString = post(url, content, charset); // 加密解密测试

		System.out.println(jsonString);
	}

	public static String getString(HttpServletRequest request, String paramName) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals(""))
			return temp;
		else
			return null;
	}

	public static String getString(HttpServletRequest request, String paramName, String defaultString) {
		String temp = getString(request, paramName);
		if (temp == null)
			temp = defaultString;
		return temp;
	}

	public static int getInt(HttpServletRequest request, String paramName) throws NumberFormatException {
		return Integer.parseInt(getString(request, paramName));
	}

	public static int getInt(HttpServletRequest request, String paramName, int defaultInt) {
		try {
			String temp = getString(request, paramName);
			if (temp == null)
				return defaultInt;
			else
				return Integer.parseInt(temp);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String getParameter(HttpServletRequest request, String paramName) {
		return getParameter(request, paramName, false);
	}

	// �Կ��ַ���д���
	public static String getParameter(HttpServletRequest request, String paramName, String defaultStr) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			if (temp.equals("")) {
				return defaultStr;
			} else {
				return nullToString(temp);
			}
		} else {
			return defaultStr;
		}

	}

	public static String getEscapeHTMLParameter(HttpServletRequest request, String paramName) {
		return nullToString(StringUtil.escapeHTMLTags(BeanUtil.getParameter(request, paramName, true)));
	}

	public static String getParameter(HttpServletRequest request, String paramName, boolean emptyStringsOK) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {
				return "";
			} else {
				return temp;
			}
		} else {
			return "";
		}
	}

	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultNum) {
		String temp = request.getParameter(paramName);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	public static int getIntParameter(HttpServletRequest request, String paramName) {
		return getIntParameter(request, paramName, 0);
	}

	public static String nullToString(String oldString) {
		if (oldString == null) {
			return "";
		}
		return oldString;
	}

	public static String nullToString(String oldString, String defaultValue) {
		oldString = nullToString(oldString);
		if ("".equals(oldString)) {
			return defaultValue;
		}
		return oldString;
	}

	// �޸�ʹ�õķ���
	public static String getRequestString(HttpServletRequest request, String s) {
		s = nullToString(s).trim();
		s = BeanUtil.getEscapeHTMLParameter(request, s);
		s = StringUtil.toChinese(s);
		s = StringUtil.toUnicode(s);
		s = StringUtil.StringtoSql(s);
		return s;
	}

	// ���ʹ�õķ���
	public static String getRequestString1(HttpServletRequest request, String s) {
		s = nullToString(s).trim();
		s = BeanUtil.getEscapeHTMLParameter(request, s);
		// ת��
		// s=StringUtil.toChinese(s);
		s = StringUtil.toUnicode(s);
		s = StringUtil.StringtoSql(s);
		return s;
	}

	public static String getSqlString(String s) {
		s = StringUtil.SqltoString(s);
		s = StringUtil.toChinese(s);
		s = nullToString(s).trim();
		return s;
	}

	public static String getEscapeHTMLParameter1(HttpServletRequest request, String s) {
		return nullToString(StringUtil.escapeHTMLTags(request.getParameter(s)));
	}

	public static int getIntParameter1(HttpServletRequest request, String s) {
		int defaultNum = 0;
		String temp = request.getParameter(s);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp);
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

}
