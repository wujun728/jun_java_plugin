package klg.common.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;



public class MyPrinter2 {

	static final String SPLIT_LINE = "=";// 分割线
	static final String MY_SIGN = "KLG_print";// 默認標記
	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将集合类型toSring方法
	 * 
	 * @param object
	 * @param recursion
	 *            是否递归
	 * @return
	 */
	private static String collectionToStr(Object object, boolean recursion) {
		if (object == null)
			return "null";
		Object[] a = null;
		// 将集合类型转换成数组类型
		if (isArrayType(object))
			a = (Object[]) object;
		else
			a = ((Collection) object).toArray();
		if (isSimpleArr(a) || !recursion)
			return Arrays.toString(a);
		else
			return complexArrToStr(a);
	}

	/**
	 * Arrays有toString方法，但是对象内容太多，在一行显示 还有就是没有显示index信息
	 */
	private static String complexArrToStr(Object[] a) {
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			String value = objToStr(a[i], false);
			b.append("[" + i + "]" + " -> " + value);
			if (i == iMax)
				return b.toString();
			b.append(", \r\n");
		}
	}

	/**
	 * map类型toString方法
	 * 
	 * @param map
	 * @param recursion
	 *            是否递归
	 * @return
	 */
	private static String mapToStr(Map<String, Object> map, boolean recursion) {
		if (map == null)
			return "null";
		if (isSimpleMap(map) || !recursion)
			return simpleMapToStr(map);
		else
			return complexMapToStr(map, true);
	}

	/**
	 * map的value是简单类型的，复制Map.toString，我给它加了换行10个换行
	 * 
	 * @param map
	 * @return
	 */
	private static String simpleMapToStr(Map<String, Object> map) {
		Iterator<Entry<String, Object>> i = map.entrySet().iterator();
		if (!i.hasNext())
			return "{}";

		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (int t = 1;; t++) {
			Entry<String, Object> e = i.next();
			sb.append(e.getKey()).append(" = ").append(e.getValue());
			if (!i.hasNext())
				return sb.append('}').toString();
			sb.append(',').append(' ');
			if (t % 10 == 0 && t != 0)
				sb.append("\r\n ");
		}
	}

	private static String complexMapToStr(Map<String, Object> map, boolean recursion) {
		Iterator<Entry<String, Object>> i = map.entrySet().iterator();
		if (!i.hasNext())
			return "{}";
		StringBuilder sb = new StringBuilder();
		sb.append("{\r\n");
		for (int t = 1;; t++) {
			Entry<String, Object> e = i.next();
			String key = e.getKey();
			Object value = e.getValue();
			sb.append(indent(2, " ")).append(key).append(" = ");
			if (isSimpleType(value) || !recursion)
				sb.append(String.valueOf(value));
			else
				sb.append(objToStr(value, false));
			if (!i.hasNext())
				return sb.append("\r\n}").toString();
			sb.append(',').append("\r\n");
		}
	}

	/**
	 * 
	 * 
	 * @param object
	 * @param recursion
	 *            是否要递归
	 * @return
	 */
	private static String beanToStr(Object object, boolean recursion) {
		if (object == null)
			return "null";
		Class clazz = object.getClass();
		StringBuilder sb = new StringBuilder();
		// 返回源代码中给出的底层类的简称
		sb.append(clazz.getSimpleName()).append("[");
		Field[] fields = sortFieldByType(clazz.getDeclaredFields());
		int iMax = fields.length - 1;
		if (iMax == -1)
			return sb.append("]").toString();
		for (int i = 0;; i++) {
			Field field = fields[i];
			field.setAccessible(true);// 设置些属性是可以访问的
			String name = field.getName();// 取得field的名称
			if (name.equals("serialVersionUID"))
				continue;
			try {
				Object value = field.get(object);// 得到此属性的值
				if (isSimpleType(value) || !recursion)
					sb.append(name + " = " + String.valueOf(value));
				else
					sb.append("\r\n" + indent(clazz.getSimpleName().length() + 2, " ")
							+ objToStr(value, false) + "\r\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (i == iMax)
				return sb.append("]").toString();
			sb.append(",");
		}
	}

	private static String indent(int length, String sign) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(sign);
		}
		return sb.toString();
	}

	private static boolean isSimpleType(Object obj) {
		if (obj == null)
			return true;
		else {
			Class objectClass = obj.getClass();
			return isSimpleType(objectClass);
		}
	}

	/**
	 * 
	 * @param objectClass
	 *            用obj.getClass()取得
	 * @return
	 */
	private static boolean isSimpleType(Class objectClass) {
		if (objectClass == boolean.class || objectClass == Boolean.class
				|| objectClass == short.class || objectClass == Short.class
				|| objectClass == byte.class || objectClass == Byte.class
				|| objectClass == int.class || objectClass == Integer.class
				|| objectClass == long.class || objectClass == Long.class
				|| objectClass == float.class || objectClass == Float.class
				|| objectClass == char.class || objectClass == Character.class
				|| objectClass == double.class || objectClass == Double.class
				|| objectClass == String.class) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method isCollectionType
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	private static boolean isCollectionType(Object obj) {
		if (obj == null)
			return false;
		return (obj.getClass().isArray() || (obj instanceof Collection));
	}

	private static boolean isArrayType(Object obj) {
		if (obj == null)
			return false;
		return (obj.getClass().isArray());
	}

	private static boolean isMapType(Object obj) {
		if (obj == null)
			return false;
		return (obj instanceof Map);
	}

	private static boolean isBeanType(Object obj) {
		if (isSimpleType(obj) || isCollectionType(obj) || isMapType(obj))
			return false;
		else
			return true;
	}

	private static boolean isDateType(Object obj) {
		if (obj == null)
			return false;
		return (obj instanceof Date);
	}

	private static boolean isSimpleArr(Object[] a) {
		if (a == null || a.length < 1)
			return true;
		boolean flag = true;
		for (Object o : a) {
			if (!isSimpleType(o)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	private static boolean isSimpleMap(Map map) {
		if (map == null)
			return true;
		Iterator<Entry<String, Object>> i = map.entrySet().iterator();
		boolean flag = true;
		while (i.hasNext()) {
			Entry<String, Object> e = i.next();
			if (!isSimpleType(e.getValue())) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/***
	 * 将简单类型排在前面
	 * 
	 * @param fields
	 * @return
	 */

	public static Field[] sortFieldByType(Field[] fields) {
		for (int i = 0; i < fields.length; i++) {
			if (isSimpleType(fields[i].getType()))
				continue;// fields[i]是简单类型不管
			// fields[i]是复杂类型
			// int j = i+1，从fields[i]之后开始比较
			for (int j = i + 1; j < fields.length; j++) {
				Field fieldTmp = null;
				if (isSimpleType(fields[j].getType())) {// 与后面的第一个简单类型交互
					fieldTmp = fields[i];
					fields[i] = fields[j];
					fields[j] = fieldTmp;
					break; // 后面的循环，是没有意义de
				}
			}
		}
		return fields;
	}

	/**
	 * 这个方法是递归方法，并且并多个地方调用，考虑到循环引用和显示格式， boolean recursion取得确保递归可以被终止。
	 * 
	 * @param object
	 * @param recursion
	 *            是否需要更深一层显示
	 * @return
	 */
	private static String objToStr(Object object, boolean recursion) {
		if (object == null)
			return "null";
		if (isDateType(object))
			return new SimpleDateFormat(DATE_FORMAT).format((Date) object);
		else if (isBeanType(object))
			return beanToStr(object, recursion);
		else if (isCollectionType(object))
			return collectionToStr(object, recursion);
		else if (isMapType(object))
			return mapToStr((Map) object, recursion);
		else
			return String.valueOf(object);
	}

	public static String objToStr(Object obj) {
		return objToStr(obj, true);
	}

	private static void print(Object obj, String sign, String content) {
		String begin = indent(15, SPLIT_LINE) + "  " + obj.getClass().getSimpleName() + "  >> "
				+ sign + "  " + indent(10, SPLIT_LINE);
		int length = (begin.length() - sign.length() - 5) / 2;

		String end = indent(length, SPLIT_LINE) + "  " + sign + "  " + indent(length, SPLIT_LINE);
		System.out.println(begin + "\r\n" + content + "\r\n" + end);

	}

	public static void print(Object obj) {
		print(obj, MY_SIGN, objToStr(obj));
	}

	public static void printWithSign(String sign, Object obj) {
		print(obj, sign, objToStr(obj));
	}
}
