package com.jun.plugin.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.jun.admin.framework.exception.BusinessException;

/**
 * 扩展org.apache.commons.beanutils.BeanUtils<br>
 * 
 * @author Wesley<br>
 * 
 */
public class BeanUtil extends org.apache.commons.beanutils.BeanUtils {
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
	public static void applyIf1(Object dest, Object orig) throws Exception {
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
	public static boolean checkObjProperty1(Object orig, Map dest) throws Exception {
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
	//**********************************************************************************************
	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty1(Object object, String propertyName) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty1(Object object, String propertyName, Object newValue)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod1(Object object, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
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
	public static Object invokePrivateMethod1(Object object, String methodName, Object param) 
			throws NoSuchMethodException,IllegalAccessException, InvocationTargetException {
		return invokePrivateMethod1(object, methodName, new Object[] { param });
	}
	
	
	
	
	
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	

	private static final char SEPARATOR = '_';

	/**
	 * 获取当前类声明的private/protected变量
	 */
	public static Object getPrivateProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置当前类声明的private/protected变量
	 */
	public static void setPrivateProperty(Object object, String propertyName,
			Object newValue) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}

	/**
	 * 调用当前类声明的private/protected函数
	 */
	public static Object invokePrivateMethod(Object object, String methodName,
			Object[] params) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
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
	public static Object invokePrivateMethod(Object object, String methodName,
			Object param) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return invokePrivateMethod1(object, methodName, new Object[] { param });
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
	public static <T> T populate(Class<T> cls,
			Map<String, ? extends Object> param) {
		T t = null;
		try {
			t = populate2(cls.newInstance(), param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * @param
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T toBean(Map map, Class<T> clazz) {
		try {
			T bean = clazz.newInstance();
			BeanUtil.populate(bean, map);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * @Title: describe
	 * @Description: 获得同时有get和set的field和value。
	 * @param bean
	 *            获得bean对象
	 * @return Map 返回属性列表
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static Map describe(Object bean) {
		Map des = new HashMap();
		PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
		String name = null;
		for (int i = 0; i < desor.length; i++) {
			if (desor[i].getReadMethod() != null
					&& desor[i].getWriteMethod() != null) {
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

	public static Object getFieldValue(Object object, String fieldName)
			throws NoSuchFieldException {
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
	 * @Title: setFieldValue
	 * @Description: 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * @param object
	 *            对象名
	 * @param fieldName
	 *            字段名称
	 * @param value
	 *            值
	 * @param
	 * @throws NoSuchFieldException
	 * @return void
	 * @throws
	 */
	public static void setFieldValue(Object object, String fieldName,
			Object value) throws NoSuchFieldException {
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
	 * @Title: getDeclaredField
	 * @Description: 循环向上转型,获取对象的DeclaredField.
	 * @param @param object
	 * @param @param fieldName
	 * @param @return
	 * @param @throws NoSuchFieldException
	 * @return Field
	 * @throws
	 */
	public static Field getDeclaredField(Object object, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * @Title: getDeclaredField
	 * @Description: 循环向上转型,获取类的DeclaredField.
	 * @param clazz
	 *            类名称
	 * @param fieldName
	 *            字段名称
	 * @param @throws NoSuchFieldException
	 * @return Field
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + fieldName);
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
				java.lang.reflect.Field[] fields = orig.getClass()
						.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					String name = fields[i].getName();
					if (PropertyUtils.isReadable(orig, name)
							&& PropertyUtils.isWriteable(dest, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig,
								name);
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
	public static boolean checkObjProperty(Object orig, Map dest)
			throws Exception {
		try {
			java.lang.reflect.Field[] fields = orig.getClass()
					.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String name = fields[i].getName();
				if (!dest.containsKey(name)) {
					if (PropertyUtils.isReadable(orig, name)) {
						Object value = PropertyUtils.getSimpleProperty(orig,
								name);
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

	// *****************************************************************************************************************
	// *****************************************************************************************************************
	// @Test
	/*public void test1() throws IllegalAccessException,
			InvocationTargetException {
		ConvertUtils.register(new Converter() {// 注册自定义转换器；
					public Object convert(Class type, Object value) {
						if (value == null)
							return null;
						if (!(value instanceof String)) {
							throw new ConversionException("这里只支持String类型！");
						}
						if (((String) value).trim().equals("")) {// trim:返回字符串的副本，忽略前导空白和尾部空白。
							return null;
						}
						SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");// 设置日期格式
						try {
							return df.parse((String) value); // 将字符串转为Date对象；
						} catch (ParseException e) {
							throw new RuntimeException(e);
						}
					}
				}, java.util.Date.class);
		bean bb = new bean();
		BeanUtils.setProperty(bb, "a", "faffafdas");
		BeanUtils.setProperty(bb, "b", 123);
		BeanUtils.setProperty(bb, "c", "1999-02-12");
		System.out.println(bb.getA());
		System.out.println(bb.getB());
		System.out.println(bb.getC());
	}*/

	// @Test
	/*public void test2() throws IllegalAccessException,
			InvocationTargetException {
		// map中元素必须和bean中的属性对应：
		Map<Object, String> map = new HashMap<Object, String>();
		map.put("a", "dfa");
		map.put("b", "4568");
		map.put("c", "2121-11-23");
		ConvertUtils.register(new DateLocaleConverter(), java.util.Date.class);
		bean b = new bean();
		BeanUtils.populate(b, map);
		System.out.println(b.getA());
		System.out.println(b.getB());
		System.out.println(b.getC());
	}*/

	// @Test
	/*public void test11() throws Exception {
		Person p = new Person();
		BeanUtils.setProperty(p, "age", 456);
		System.out.println(p.getAge());// 456
	}*/

	// @Test
	/*public void test12() throws Exception {
		String name = "aaaa";
		String age = "123";
		String password = "pw";
		Person p = new Person();
		// 支持8种基本类型自动转换，其他复杂类型的 转换，需要 编写 并且注册类型转换器，最常见的日起类型的 转换器。
		BeanUtils.setProperty(p, "name", name);
		BeanUtils.setProperty(p, "age", age);
		BeanUtils.setProperty(p, "password", password);
		System.out.println(p.getName());// aaaa
		System.out.println(p.getAge());// 123
		// System.out.println(p.getPassword());//pw
	}*/

	// @Test
	public void test13() throws Exception {
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
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd", Locale.US);
						try {
							return df.parse(str); // 此语句将会抛出异常，但是由于
													// 子类不能抛出比父类更多的异常，所以只能catch
						} catch (ParseException e) {
							throw new RuntimeException(e); // 抛出运行时异常并停止程序的执行，以便通知上层异常信息，
							// 并建议其修改代码，以期提高代码的严谨性。
						}
					}
				}, Date.class);
//		Person p = new Person();
//		BeanUtils.setProperty(p, "birthday", birthday);// 因上面注册了“字符串--日起类型”
														// 的类型转换器，故此就不会报错异常了。
		// System.out.println(p.getBirthday());//pw
//		System.out.println("___" + BeanUtils.getProperty(p, "birthday"));
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
	public void test15() throws Exception {
		Map map = new HashMap();
		map.put("name", "aaa");
		map.put("password", "123");
		map.put("brithday", "1980-09-09");
		ConvertUtils.register(new DateLocaleConverter(), Date.class);
//		Person p = new Person();
		// 用map集合填充bean属性,map关键字和bean属性要一致
//		BeanUtil.populate(p, map);
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
	public void invoke1() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, IntrospectionException {
//		Person person = Person.class.newInstance();
		BeanInfo beaninfo = Introspector.getBeanInfo(PropertyDescriptor.class);
		PropertyDescriptor[] porpertydescriptors = beaninfo
				.getPropertyDescriptors();
		for (PropertyDescriptor pd : porpertydescriptors) {
			System.err.println(pd.getName()); // 打印出所有的属性
			// 这里是需要判断的，咱们已经打印出所有属性的名字，然后想修改哪个属性在来判断就很方便了
			// 如果属性的名字和name一样
			if (pd.getName().equals("name")) {
				// 找到我们需要的属性，然后通过反射获取该属性的get和set方法
				Method setMethod = pd.getWriteMethod();// 获得该属性的set方法
				Method getMethod = pd.getReadMethod(); // 获得该属性的get方法
				// 然后咱们调用获得的set方法来设置属性的value
				setMethod.invoke(beaninfo, "温家宝");
				// 设置好后咱们来调用属性的get方法来看看是否设置成功
				System.out.println(getMethod.invoke(beaninfo));// 属性的get方法一都没有参数，所以是null，也可以不写
				break;
			}
		}
	}

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
	public void invoke2() throws InstantiationException,
			IllegalAccessException, IntrospectionException,
			IllegalArgumentException, InvocationTargetException {
		// 首先得到Person类的一个对象
//		Person person = Person.class.newInstance();
		// 然后得到咱们需要设置的某个属性(比如age)的属性描述器
		// 参数分别为需要设置哪个类的哪个属性
//		PropertyDescriptor pd = new PropertyDescriptor("age", Person.class);
		// 得到该属性的描述器后，咱们就可以通过反射来得到该属性的get和set方法了
//		Method setMethod = pd.getWriteMethod();// 获得该属性的set方法
//		Method getMethod = pd.getReadMethod(); // 获得该属性的get方法
//		setMethod.invoke(person, 56);
//		System.out.println(getMethod.invoke(person));
	}

	// @Test
	public void invoke3() throws IllegalAccessException,
			InvocationTargetException {
		// 先创建出Person对象
//		Person person = new Person();
		// 设置name属性的值
		// 参数如下：为哪个对象，哪个属性名，赋什么属性值
//		BeanUtils.copyProperty(person, "name", "胡锦涛");
		// 打印一下，看name的属性值是否设置成功
//		System.out.println(person.getName());
	}

	// @Test
	// 此单元是用Map集合的方式给属性赋值
	public void invoke4() throws IllegalAccessException,
			InvocationTargetException {
		// 先创建出Person对象
//		Person person = new Person();
		// 创建Map集合
		Map map = new HashMap();
		// 向集合中添加元素,这里添加的就是属性名和属性值
		map.put("name", "李嘉诚");
		map.put("age", "65");
		// 调用populate方法来设置value，参数分别是为哪个对象设置value，存放键值的集合
//		BeanUtil.populate(person, map);
		// 这样就将属性值都设置好了，打印一下看是否成功设置
//		System.out.println(person.getAge());
//		System.out.println(person.getName());
		// 上面的代码其实有个小问题，age属性其实是int类型，但Map集合中存放的都是String类型的
		// 实际上运行没有错误，但我也不知道存在一些什么隐患，以后遇到在补充吧
	}

	// @Test
	/*public void invoke5() throws IllegalAccessException,
			InvocationTargetException {
		Person person1 = new Person();
		Person person2 = new Person();
		// 咱们先设置person1的属性
		person1.setName("邓小平");
		person1.setAge(98);
		// 然后将person1的属性赋给person2
		BeanUtils.copyProperties(person2, person1);
		// 打印出person2中的属性
		System.out.println(person2.getAge());
		System.out.println(person2.getName());
	}*/

	// *****************************************************************************************************************
	// *****************************************************************************************************************
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
				nextUpperCase = Character
						.isUpperCase(inputString.charAt(i + 1));
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
	public static String toCamelCaseString(String inputString,
			boolean firstCharacterUppercase) {
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
			if (Character.isUpperCase(inputString.charAt(0))
					&& !Character.isUpperCase(inputString.charAt(1))) {
				answer = inputString.substring(0, 1).toLowerCase(Locale.US)
						+ inputString.substring(1);
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

	public static void main(String[] args) {
		System.out.println(BeanUtil.toUnderlineString("ISOCertifiedStaff"));
		System.out.println(BeanUtil.getValidPropertyName("CertifiedStaff"));
		System.out.println(BeanUtil.getSetterMethodName("userID"));
		System.out.println(BeanUtil.getGetterMethodName("userID"));
		System.out.println(BeanUtil.toCamelCaseString("iso_certified_staff",
				true));
		System.out.println(BeanUtil.getValidPropertyName("certified_staff"));
		System.out.println(BeanUtil.toCamelCaseString("site_Id"));
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
	public static Map toMap(Object object) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
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
	public static Collection toMapList(Collection collection)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List retList = new ArrayList();
		if (collection != null && !collection.isEmpty()) {
			for (Object object : collection) {
				Map map = toMap(object);
				retList.add(map);
			}
		}
		return retList;
	}

	/**
	 * 转换为Collection,同时为字段做驼峰转换>
	 * 
	 * @param collection
	 *            待转换对象集合
	 * @return 转换后的Collection>
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	/*
	 * public static Collection toMapListForFlat(Collection collection) throws
	 * IllegalAccessException, InvocationTargetException, NoSuchMethodException
	 * { List retList = new ArrayList(); if (collection != null &&
	 * !collection.isEmpty()) { for (Object object : collection) { Map map =
	 * toMapForFlat(object); retList.add(map); } } return retList; }
	 */

	/**
	 * 转换成Map并提供字段命名驼峰转平行
	 * 
	 * @param clazz
	 *            目标对象所在类
	 * @param object
	 *            目标对象
	 * @param map
	 *            待转换Map
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	/*
	 * public static Map toMapForFlat(Object object) throws
	 * IllegalAccessException, InvocationTargetException, NoSuchMethodException
	 * { Map map = toMap(object); return toUnderlineStringMap(map); }
	 */

	/**
	 * 将Map的Keys去下划线 (例:branch_no -> branchNo )
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	/*
	 * public static Map toCamelCaseMap(Map map) { Map newMap = new HashMap();
	 * for (String key : map.keySet()) { safeAddToMap(newMap,
	 * MapUtils.toCamelCaseString(key), map.get(key)); } return newMap; }
	 */

	/**
	 * 将Map的Keys转译成下划线格式的 (例:branchNo -> branch_no)
	 * 
	 * @param map
	 *            待转换Map
	 * @return
	 */
	/*
	 * public static Map toUnderlineStringMap(Map map) { Map newMap = new
	 * HashMap(); for (String key : map.keySet()) {
	 * newMap.put(MapUtils.toUnderlineString(key), map.get(key)); } return
	 * newMap; }
	 */
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	public static <T>T populate3(T t,Map<String,? extends Object> param){
		try{
			org.apache.commons.beanutils.BeanUtils.populate(t, param);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return t;
	}
	public static <T>T populate4(Class<T> cls,Map<String,? extends Object> param){
		T t = null;
		try {
			t = populate2(cls.newInstance(),param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
	
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/**
	 * Beanutils只可以默认转换了最基本的类型
	 * 如果希望设置其他类型，可以自己转换
	 * 或是注册转换器
	 * 如果设置的没有某个属性，则会忽略
	 * @throws Exception
	 */
	
	
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/


	  private static void convert(Object dest, Object orig) throws
	      IllegalAccessException, InvocationTargetException {

	      // Validate existence of the specified beans
	      if (dest == null) {
	          throw new IllegalArgumentException
	              ("No destination bean specified");
	      }
	      if (orig == null) {
	          throw new IllegalArgumentException("No origin bean specified");
	      }

	      // Copy the properties, converting as necessary
	      if (orig instanceof DynaBean) {
	          DynaProperty origDescriptors[] =
	              ( (DynaBean) orig).getDynaClass().getDynaProperties();
	          for (int i = 0; i < origDescriptors.length; i++) {
	              String name = origDescriptors[i].getName();
	              if (PropertyUtils.isWriteable(dest, name)) {
	                  Object value = ( (DynaBean) orig).get(name);
	                  try {
	                      copyProperty(dest, name, value);
	                  }
	                  catch (Exception e) {
	                      ; // Should not happen
	                  }

	              }
	          }
	      }
	      else if (orig instanceof Map) {
	          Iterator names = ( (Map) orig).keySet().iterator();
	          while (names.hasNext()) {
	              String name = (String) names.next();
	              if (PropertyUtils.isWriteable(dest, name)) {
	                  Object value = ( (Map) orig).get(name);
	                  try {
	                      copyProperty(dest, name, value);
	                  }
	                  catch (Exception e) {
	                      ; // Should not happen
	                  }

	              }
	          }
	      }
	      else
	      /* if (orig is a standard JavaBean) */
	      {
	          PropertyDescriptor origDescriptors[] =
	              PropertyUtils.getPropertyDescriptors(orig);
	          for (int i = 0; i < origDescriptors.length; i++) {
	              String name = origDescriptors[i].getName();
//	              String type = origDescriptors[i].getPropertyType().toString();
	              if ("class".equals(name)) {
	                  continue; // No point in trying to set an object's class
	              }
	              if (PropertyUtils.isReadable(orig, name) &&
	                  PropertyUtils.isWriteable(dest, name)) {
	                  try {
	                      Object value = PropertyUtils.getSimpleProperty(orig, name);
	                      copyProperty(dest, name, value);
	                  }
	                  catch (java.lang.IllegalArgumentException ie) {
	                      ; // Should not happen
	                  }
	                  catch (Exception e) {
	                      ; // Should not happen
	                  }

	              }
	          }
	      }

	  }

	  
	  /**
		 * 对象拷贝
		 * 数据对象空值不拷贝到目标对象
		 * 
		 * @param dataObject
		 * @param toObject
		 * @throws NoSuchMethodException
		 * copy
		 */
	  public static void copyBeanNotNull2Bean(Object databean,Object tobean)throws Exception
	  {
		  PropertyDescriptor origDescriptors[] =
	          PropertyUtils.getPropertyDescriptors(databean);
	      for (int i = 0; i < origDescriptors.length; i++) {
	          String name = origDescriptors[i].getName();
//	          String type = origDescriptors[i].getPropertyType().toString();
	          if ("class".equals(name)) {
	              continue; // No point in trying to set an object's class
	          }
	          if (PropertyUtils.isReadable(databean, name) &&
	              PropertyUtils.isWriteable(tobean, name)) {
	              try {
	                  Object value = PropertyUtils.getSimpleProperty(databean, name);
	                  if(value!=null){
	                	    copyProperty(tobean, name, value);
	                  }
	              }
	              catch (java.lang.IllegalArgumentException ie) {
	                  ; // Should not happen
	              }
	              catch (Exception e) {
	                  ; // Should not happen
	              }

	          }
	      }
	  }
	  
	  
	  /**
	   * 把orig和dest相同属性的value复制到dest中
	   * @param dest
	   * @param orig
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public static void copyBean2Bean(Object dest, Object orig) throws Exception {
	      convert(dest, orig);
	  }

	  public static void copyBean2Map(Map map, Object bean){
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
		for (int i =0;i<pds.length;i++)
		{
			PropertyDescriptor pd = pds[i];
			String propname = pd.getName();
			try {
				Object propvalue = PropertyUtils.getSimpleProperty(bean,propname);
				map.put(propname, propvalue);
			} catch (IllegalAccessException e) {
				//e.printStackTrace();
			} catch (InvocationTargetException e) {
				//e.printStackTrace();
			} catch (NoSuchMethodException e) {
				//e.printStackTrace();
			}
		}
	  }

	  /**
	   * 将Map内的key与Bean中属性相同的内容复制到BEAN中
	   * @param bean Object
	   * @param properties Map
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public static void copyMap2Bean(Object bean, Map properties) throws
	      IllegalAccessException, InvocationTargetException {
	      // Do nothing unless both arguments have been specified
	      if ( (bean == null) || (properties == null)) {
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
	              setProperty(bean, name, value);
	          }
	          catch (NoSuchMethodException e) {
	              continue;
	          }
	      }
	  }
	  

	  /**
	   * 自动转Map key值大写
	   * 将Map内的key与Bean中属性相同的内容复制到BEAN中
	   * @param bean Object
	   * @param properties Map
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public static void copyMap2Bean_Nobig(Object bean, Map properties) throws
	      IllegalAccessException, InvocationTargetException {
	      // Do nothing unless both arguments have been specified
	      if ( (bean == null) || (properties == null)) {
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
	              setProperty(bean, name, value);
	          }
	          catch (NoSuchMethodException e) {
	              continue;
	          }
	      }
	  }

	  /**
	   * Map内的key与Bean中属性相同的内容复制到BEAN中
	   * 对于存在空值的取默认值
	   * @param bean Object
	   * @param properties Map
	   * @param defaultValue String
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public static void copyMap2Bean(Object bean, Map properties, String defaultValue) throws
	      IllegalAccessException, InvocationTargetException {
	      // Do nothing unless both arguments have been specified
	      if ( (bean == null) || (properties == null)) {
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
	              setProperty(bean, name, value);
	          }
	          catch (NoSuchMethodException e) {
	              continue;
	          }
	      }
	  }
	  
	/*****************************************************************************************************/
	/*****************************************************************************************************/
	/*****************************************************************************************************/
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
//			return StringUtils.join(list.toArray(), separator);//***
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
}
