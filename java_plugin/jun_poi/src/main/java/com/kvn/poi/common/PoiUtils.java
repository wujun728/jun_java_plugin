package com.kvn.poi.common;

import java.math.BigDecimal;
import java.sql.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
* @author wzy
* @date 2017年7月12日 下午12:53:34
*/
public class PoiUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T parse2Type(Object value, Class<T> type){
		if(String.class.isAssignableFrom(type)){
			return (T) value.toString();
		}
		
		if(isPrimitiveOrWrapClass(type)){
			String className = type.getName();
	        switch (className) {
	            case "java.lang.Boolean":
	            case "boolean":
	                return (T) Boolean.valueOf(value.toString());
	            case "java.lang.Character":
	            case "char":
	                return (T) Character.valueOf(value.toString().charAt(0));
	            case "java.lang.Byte":
	            case "byte":
	                return (T) Byte.valueOf(value.toString());
	            case "java.lang.Short":
	            case "short":
	                return (T) Short.valueOf(value.toString());
	            case "java.lang.Integer":
	            case "int":
	                return (T) Integer.valueOf(value.toString());
	            case "java.lang.Long":
	            case "long":
	                return (T) Long.valueOf(value.toString());
	            case "java.lang.Float":
	            case "float":
	                return (T) Float.valueOf(value.toString());
	            case "java.lang.Double":
	            case "double":
	                return (T) Double.valueOf(value.toString());
	            default:
	                throw new RuntimeException(className + "不支持，bug");
	        }
		}
		
		if(Date.class.isAssignableFrom(type)){
			// TODO
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
			return (T) DateTime.parse(value.toString(), formatter);
		}
		
		if(BigDecimal.class.isAssignableFrom(type)){
			return (T) new BigDecimal(value.toString());
		}
		
		throw new RuntimeException("不支持的类型：" + type.getName());
	}
	
	
	/**
	 * 决断clz是否是基本类型 或者 基本类型的包装类型
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitiveOrWrapClass(Class<?> clz){
		if(clz.isPrimitive()){
			return true;
		}

		return isWrapClass(clz);
	}

	public static boolean isWrapClass(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

}
