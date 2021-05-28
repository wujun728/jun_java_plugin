package org.typroject.tyboot.core.foundation.utils;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: Bean.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:普通javaBean的工具类，包含对Bean的属性的各种操作
 *  TODO
 * 
 *  Notes:
 *  $Id: Bean.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class Bean {

	private static final String SEPARATOR = ".";


	//使用map作为本地缓存，消除反射带来的性能问题。
	private static Map<String,Map<String,Class<?>>> fieldsMap			 = new HashMap<>();
	private static Map<String,List<Field>> allFields			 		 = new HashMap<>();
	private static Map<String,MethodAccess> methodAccessMap			 	 = new HashMap<>();
	private static Map<String,String[]> methodParameterNamesMap			 = new HashMap<>();
	private static Map<String,Method> methodMap							 = new HashMap<>();
	private static Map<String,Class> classMap							 = new HashMap<>();


	public static MethodAccess getMethodAccess(Class<?> beanClass)
	{
		MethodAccess methodAccess = methodAccessMap.get(beanClass.getName());
		if(ValidationUtil.isEmpty(methodAccess))
		{
			methodAccess = MethodAccess.get(beanClass);
			methodAccessMap.put(beanClass.getName(),methodAccess);
		}
		return methodAccess;
	}


	/**
	 * 获取对象的所有字段名称及其类型
	 * @param beanClass
	 * @return
	 */
	public  static Map<String, Class<?>> getFieldsMap(Class<?> beanClass){
		String key 						= beanClass.getName();
		Map<String, Class<?>> fieldMap 	= fieldsMap.get(key);
		if(ValidationUtil.isEmpty(fieldMap))
		{
			fieldMap 		= new HashMap<>();
			Field[] fields 	= beanClass.getDeclaredFields();
			for (Field field : fields) {
				fieldMap.put(field.getName(), field.getType());
			}
			if (beanClass.getSuperclass() != null) {
				fieldMap.putAll(getFieldsMap(beanClass.getSuperclass()));
			}
			fieldsMap.put(key,fieldMap);
		}
		return fieldMap;
	}

	/* 获取类及其父类的Field对象列表 */
	public  static List<Field> getAllFields(Class<?> beanClass) {
		String key = beanClass.getName();

		List<Field> result = allFields.get(key);
		if(ValidationUtil.isEmpty(result))
		{
			result = new ArrayList<>();
			result.addAll(Arrays.asList(beanClass.getDeclaredFields()));
			if (beanClass.getSuperclass() != null) {
				result.addAll(getAllFields(beanClass.getSuperclass()));
			}
			allFields.put(key,result);
		}

		return result;
	}

	/**
	 * TODO. 将source中不为空的字段值复制到对应的taget中的字段
	 * 
	 * @param sourceBean
	 * @param targetBean
	 * @return
	 */
	public static <S, T> T copyExistPropertis(S sourceBean, T targetBean) {

			if (sourceBean != null && targetBean != null) {
				Map<String, Class<?>> fields = getFieldsMap(sourceBean.getClass());
				Map<String, Class<?>> targetFields = getFieldsMap(targetBean.getClass());
				Class<?> sourceBeanClass = sourceBean.getClass();
				Class<?> targetBeanClass = targetBean.getClass();
				Object sourcePropertyValue = null;

				for (String fieldName : fields.keySet()) {
					if (fieldName.equals("serialVersionUID"))
						continue;

					MethodAccess sourceMethodAccess = getMethodAccess(sourceBeanClass);
					if (!ValidationUtil.isEmpty(sourceMethodAccess)) {
						sourcePropertyValue = sourceMethodAccess.invoke(sourceBean,property2GetMethod(fieldName));
					}
					MethodAccess targetMethodAccess = getMethodAccess(targetBeanClass);
					if (!ValidationUtil.isEmpty(targetMethodAccess) && !ValidationUtil.isEmpty(targetFields.get(fieldName))) {

						if (targetFields.get(fieldName).equals(String.class)) {
							if (sourcePropertyValue != null) {
									targetMethodAccess.invoke(targetBean,property2SetMethod(fieldName),sourcePropertyValue);
							}
						} else {
							if (!ValidationUtil.isEmpty(sourcePropertyValue)) {
									targetMethodAccess.invoke(targetBean,property2SetMethod(fieldName),sourcePropertyValue);
							}
						}
					}
				}
			}
		return targetBean;
	}
	
	public static <M,E> E toPo(M model, E entity) {
		if(model != null && entity != null) copyExistPropertis(model, entity);
		else {entity = null;}
		return entity;
	}

	public static <M,E> M toModel(E entity, M model)  {
		if(model != null && entity != null)
		{copyExistPropertis(entity, model);}
		else {model = null;}
		return model;
	}

	public static <M,E> ArrayList<M> toModels(List<E> entities,Class<M> modelBeanClass) {
		ArrayList<M> modelList = new ArrayList<M>();
		if(!ValidationUtil.isEmpty(entities))
		{
			for (E entity : entities) {
				M currentModel = null;
				try {
					currentModel =  modelBeanClass.newInstance();
					modelList.add(toModel(entity,currentModel));
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e.getMessage(),e.getCause());
				}

			}
		}
		return modelList;
	}

	/**
	 * TODO.将数据库查询的结果集转换为自定义对象
	 * 
	 * @param map
	 * @param beanClass
	 * @return
	 */
	public static Object mapToBean(Map<String, Object> map, Class<?> beanClass)  {
		Map<String, Class<?>> fieldsMap = getFieldsMap(beanClass);
		Object result = null;
		try {
			result = beanClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(),e.getCause());
		}
		Object currentMapValue = null;
		for (String fieldName : fieldsMap.keySet()) {
			if (fieldName.equals("serialVersionUID"))
				continue;
			currentMapValue = map.get(fieldName);
			if (!ValidationUtil.isEmpty(currentMapValue)) {
				if (currentMapValue instanceof BigDecimal) {
					currentMapValue = ((BigDecimal) currentMapValue).doubleValue();
				}
				if (currentMapValue instanceof BigInteger) {
					currentMapValue = ((BigInteger) currentMapValue).longValue();
				}
				MethodAccess methodAccess = getMethodAccess(beanClass);
				if (!ValidationUtil.isEmpty(methodAccess)) {
					methodAccess.invoke(result,property2SetMethod(fieldName),currentMapValue);
				}
			}
		}
		return result;
	}
	/**
	 * 将数据库查询的结果集转换为自定义对象结果集
	 * 
	 * @param listMap
	 * @param T
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> listMap2ListBean(List<Map<String, Object>> listMap, Class<T> T)  {
		List<T> list = new ArrayList<T>();
		if(!ValidationUtil.isEmpty(listMap)){
			for(Map<String, Object> map : listMap){
				list.add((T) Bean.mapToBean(map, T));
			}
		}
		return list;
	}



	public static <T> List<Map> listBean2ListMap(List<T> listBean, Class<T> T) {
		List<Map> list = new ArrayList<Map>();
		if(!ValidationUtil.isEmpty(listBean)){
			for(T t : listBean){
				list.add(Bean.BeantoMap(t));
			}
		}
		return list;
	}


	/**
	 * 将列表数据转换为，按内部对象的指定属性为key的map
	 * @param beanList
	 * @param propertyName
	 * @param <T>
	 * @return
	 */
	public static <T> Map<Object,List<T>> list2MapList(List<T> beanList,String propertyName)
	{
		Map<Object,List<T>> returnMap = new HashMap<>();
		if(!ValidationUtil.isEmpty(beanList))
		{
			Set<Object> keys = new HashSet<>();
			for(T t:beanList)
				keys.add(getPropertyValue(propertyName,t));

			for(Object key :keys)
			{
				List<T> innerList 	= returnMap.get(key);
				if(ValidationUtil.isEmpty(innerList))
					innerList 		= new ArrayList<>();
				for(T innert:beanList)
				{
					Object innerkey = getPropertyValue(propertyName,innert);

					if(key.equals(innerkey))
						innerList.add(innert);
				}
				returnMap.put(key,innerList);
			}
		}
		return returnMap;
	}


	/**
	 * TODO.将javaBean及其父对象的属性值转换为Map
	 * 
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, Object> BeantoMap(T bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Field> fields = getAllFields(bean.getClass());
		for (int i = 0; i < fields.size(); i++) {
			String fieldName = fields.get(i).getName();
			if (fieldName.equals("serialVersionUID"))
				continue;
			map.put(fieldName, getPropertyValue(fieldName, bean));
		}
		return map;
	}

	/**
	 * 
	 * 列表转map
	 *
	 * @param list
	 * @param keyProperty
	 *            作为key的属性名称
	 * @param beanClass
	 * @return
	 */
	public static <T> Map<Object, T> listToMap(List<T> list, String keyProperty, Class<?> beanClass) {
		Map<Object, T> returnMap = new HashMap<Object, T>();
			for (T t : list) {
				MethodAccess methodAccess = getMethodAccess(beanClass);
				if (!ValidationUtil.isEmpty(methodAccess)) {
					Object obj = methodAccess.invoke(t,property2GetMethod(keyProperty));
					if (!ValidationUtil.isEmpty(obj)) {
						returnMap.put(obj, t);
					}
				}
			}

		return returnMap;
	}

	/**
	 * 列表转map
	 * 
	 * @param list
	 *            对象列表
	 * @param keyProperty
	 *            指定的属性为key
	 * @param valueProperty
	 *            指定属性为 value
	 * @param beanClass
	 *            实体类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, T> Map<K, Object> listToMap(List<T> list, String keyProperty, String valueProperty,
			Class<?> beanClass) {
		Map<K, Object> returnMap = new HashMap<K, Object>();
		for (T t : list) {
			K key = null;
			Object keyObject = getPropertyValue(keyProperty, t);
			if (!ValidationUtil.isEmpty(keyObject)) {
				key = (K) keyObject;
			} else {
				throw new RuntimeException("the key property value is can not be null." + keyProperty);
			}

			Object valueObject = getPropertyValue(valueProperty, t);
			returnMap.put(key, valueObject);
		}

		return returnMap;
	}

	public static <T> Object getPropertyValue(String propertyName, T bean) {
		MethodAccess methodAccess = getMethodAccess(bean.getClass());
		Object propertyValue = null;
			if (!ValidationUtil.isEmpty(methodAccess)) {
				propertyValue =methodAccess.invoke(bean,property2GetMethod(propertyName));
			} else {
				throw new RuntimeException("property not found: " + propertyName + "in Class:" + bean.getClass().getName());
			}
		return propertyValue;
	}

	public static <T> Object setPropertyValue(String propertyName, Object propertyValue, T bean)  {
			MethodAccess methodAccess = getMethodAccess(bean.getClass());
			if (!ValidationUtil.isEmpty(methodAccess)) {
				methodAccess.invoke(bean,property2SetMethod(propertyName),propertyValue);
			} else {
				throw new RuntimeException("property not found: " + propertyName + "in Class:" + bean.getClass().getName());
			}
		return propertyValue;
	}


	/**
	 * 获取方法参数名称列表
	 * @param clazz
	 * @param method
	 * @return
	 */
	public static String[] getMethodParameterNamesByAsm4(Class<?> clazz, final Method method)  {

		String key = clazz.getName()+SEPARATOR+method.getName();
		String [] returnParameterNames = methodParameterNamesMap.get(key);
		if(ValidationUtil.isEmpty(returnParameterNames))
		{
			final Class<?>[] parameterTypes = method.getParameterTypes();

			if (parameterTypes == null || parameterTypes.length == 0) {
				return null;
			}
			final Type[] types = new Type[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				types[i] = Type.getType(parameterTypes[i]);
			}
			final String[] parameterNames = new String[parameterTypes.length];

			String className 		= clazz.getName();
			int lastDotIndex 		= className.lastIndexOf(".");
			className 				= className.substring(lastDotIndex + 1) + ".class";
			InputStream is 			= clazz.getResourceAsStream(className);
			ClassReader classReader = null;
			try {
				classReader = new ClassReader(is);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
			classReader.accept(new ClassVisitor(Opcodes.ASM5) {
				@Override
				public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
					// 只处理指定的方法
					Type[] argumentTypes = Type.getArgumentTypes(desc);
					if (!method.getName().equals(name) || !Arrays.equals(argumentTypes, types)) {
						return null;
					}
					return new MethodVisitor(Opcodes.ASM5) {
						@Override
						public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
							// 静态方法第一个参数就是方法的参数，如果是实例方法，第一个参数是this
							if (Modifier.isStatic(method.getModifiers())) {
								parameterNames[index] = name;
							}
							else if (index > 0 && index <= parameterTypes.length) {
								parameterNames[index - 1] = name;
							}
						}
					};

				}
			}, 0);
			returnParameterNames =  parameterNames;
			methodParameterNamesMap.put(key,returnParameterNames);
		}

		return returnParameterNames;

	}



	/**
	 * 对象属性转换为字段  例如：userName to USER_NAME ,约定数据库字段为全大写 下划线分割，属性名称驼峰格式
	 * @param property 字段名
	 * @return
	 */
	public static String propertyToColum(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return StringUtils.upperCase(sb.toString());
	}


	/**
	 * 字段转换成对象属性 例如：USER_NAME to userName  约定数据库字段为全大写 下划线分割，属性名称驼峰格式
	 * @param colum
	 * @return
	 */
	public static String columToProperty(String colum) {
		if (ValidationUtil.isEmpty(colum)) {
			return "";
		}
		colum = StringUtils.lowerCase(colum);
		char[] chars = colum.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static  Map<String,String> propertyToColum(String[] propertis)
	{
		Map<String,String> colMap = new HashMap<>();

		for(String p : propertis)
			colMap.put(p,propertyToColum(p));
		return colMap;
	}

	public static  String[] propertyToColums(String[] propertis)
	{
		String [] columns = new String[propertis.length];
		for(int i=0;i<propertis.length;i++)
			columns[i] = propertyToColum(propertis[i]);
		return columns;
	}

	public static  String [] ColumToProperty(String[] colums)
	{
		String [] propertis = new String[colums.length];
		for(int i=0;i<colums.length;i++)
			propertis[i] = columToProperty(colums[i]);
		return propertis;
	}


	/**
	 * 根據名稱獲得方法對象，成員方法不能存在重載，否則將獲取錯誤
	 * @param methodName
	 * @param clzz
	 * @return
	 */
	public static Method  getMethodByName(String methodName,Class clzz)
	{
		String key = clzz.getName()+SEPARATOR+methodName;
		Method returnMethod = methodMap.get(key);
		if(ValidationUtil.isEmpty(returnMethod))
		{
			Method [] methods = clzz.getDeclaredMethods();

			for(Method method : methods)
				if(methodName.equals(method.getName()))
					returnMethod = method;
			methodMap.put(key,returnMethod);
		}
		return returnMethod;
	}


	public static Class getClassByName(String className)
	{
		Class clzz = classMap.get(className);
		if(ValidationUtil.isEmpty(clzz))
		{
			try {
				clzz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(),e.getCause());
			}
			classMap.put(className,clzz);
		}

		return clzz;
	}



	public static String property2GetMethod(String propertyName)
	{
		String methodName = "get" + propertyName.substring(0,1).toUpperCase()+propertyName.substring(1,propertyName.length());
		return methodName;
	}

	public static String property2SetMethod(String propertyName)
	{
		String methodName = "set" + propertyName.substring(0,1).toUpperCase()+propertyName.substring(1,propertyName.length());
		return methodName;
	}



	public static Class<?>[] getTypesByValues(Object [] values)
	{
		Class<?>[] clzzs = new Class<?>[values.length];
		for(int i = 0;i<values.length;i++)
		{
			clzzs[i]=values[i].getClass();
		}
		return clzzs;
	}




}
