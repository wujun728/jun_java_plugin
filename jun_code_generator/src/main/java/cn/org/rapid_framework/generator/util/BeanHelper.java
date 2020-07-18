package cn.org.rapid_framework.generator.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BeanHelper {
	/**
	 * @see #org.apache.commons.beanutils.PropertyUtils.describe(obj)
	 */ 
	public static Map describe(Object obj) {
		if (obj instanceof Map)
			return (Map) obj;
		Map map = new HashMap();
		PropertyDescriptor[] descriptors = getPropertyDescriptors(obj.getClass());
		for(int i = 0; i < descriptors.length; i++ ) {
			String name = descriptors[i].getName();
            Method readMethod = descriptors[i].getReadMethod();
			if (readMethod != null) {
				try {
				    long start = System.currentTimeMillis();
					map.put(name, readMethod.invoke(obj, new Object[]{}));
					long cost = start - System.currentTimeMillis();
				}catch(Exception e){
					GLogger.warn("error get property value,name:"+name+" on bean:"+obj,e);
				}
            }
		}
		return map;
	}

   public static PropertyDescriptor getPropertyDescriptor(Class beanClass,String propertyName) {
        for(PropertyDescriptor pd : getPropertyDescriptors(beanClass)) {
            if(pd.getName().equals(propertyName)) {
                return pd;
            }
        }
        return null;
   }
	   
	public static PropertyDescriptor[] getPropertyDescriptors(Class beanClass) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(beanClass);
		} catch (IntrospectionException e) {
			return (new PropertyDescriptor[0]);
		}
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		if (descriptors == null) {
			descriptors = new PropertyDescriptor[0];
		}
		return descriptors;
	}
	

    public static PropertyDescriptor getPropertyDescriptors(Class beanClass,String name) {
        for(PropertyDescriptor pd : getPropertyDescriptors(beanClass)) {
            if(pd.getName().equals(name)) {
                return pd;
            }
        }
        return null;
    }
    
    public static void copyProperties(Object target, Object source)  {
        copyProperties(target,source,null);
    }

    public static void copyProperties(Object target, Object source,String[] ignoreProperties)  {
        if(target instanceof Map) {
            throw new UnsupportedOperationException("target is Map unsuported");
        }
        
        PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());
        List ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                try {
                    if(source instanceof Map) {
                        Map map = (Map)source;
                        if(map.containsKey(targetPd.getName())) {
                            Object value = map.get(targetPd.getName());
                            setProperty(target, targetPd, value);
                        }
                    }else {
                        PropertyDescriptor sourcePd = getPropertyDescriptors(source.getClass(), targetPd.getName());
                        if (sourcePd != null && sourcePd.getReadMethod() != null) {
                            Object value = getProperty(source, sourcePd);
                             setProperty(target, targetPd, value);
                        }
                    }
                } catch (Throwable ex) {
                    throw new IllegalArgumentException("Could not copy properties on:"+targetPd.getDisplayName(),ex);
                }
            }
        }
    }

    private static Object getProperty(Object source, PropertyDescriptor sourcePd)throws IllegalAccessException,InvocationTargetException {
        Method readMethod = sourcePd.getReadMethod();
        if (!Modifier.isPublic(readMethod.getDeclaringClass()
            .getModifiers())) {
            readMethod.setAccessible(true);
        }
        Object value = readMethod.invoke(source, new Object[0]);
        return value;
    }

    public static void setProperty(Object target, String propertyName, Object value)  {
        PropertyDescriptor pd = getPropertyDescriptor(target.getClass(),propertyName);
        if(pd == null) throw new IllegalArgumentException("not found property:"+propertyName+" on class:"+target.getClass());
        setProperty(target, pd, value);
    }
    
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void setProperty(Object target, PropertyDescriptor targetPd, Object value)  {
        Method writeMethod = targetPd.getWriteMethod();
        if (!Modifier.isPublic(writeMethod.getDeclaringClass()
            .getModifiers())) {
            writeMethod.setAccessible(true);
        }
        try {
            writeMethod.invoke(target, new Object[] { convert(value,writeMethod.getParameterTypes()[0]) });
        }catch(Exception e) {
            throw new RuntimeException("error set property:"+targetPd.getName()+" on class:"+target.getClass(),e);
        }
    }

    private static Object convert(Object value, Class<?> targetType) {
        if(value == null) return null;
        if(targetType == String.class) {
            return value.toString();
        }else {
            return convert(value.toString(),targetType);
        }
    }

    private static Object convert(String value,Class<?> targetType) {
        if(targetType == Byte.class || targetType == byte.class) {
            return new Byte(value);
        }
        if(targetType == Short.class || targetType == short.class) {
            return new Short(value);
        }
        if(targetType == Integer.class || targetType == int.class) {
            return new Integer(value);
        }
        if(targetType == Long.class || targetType == long.class) {
            return new Long(value);
        }
        if(targetType == Float.class || targetType == float.class) {
            return new Float(value);
        }
        if(targetType == Double.class || targetType == double.class) {
            return new Double(value);
        }
        if(targetType == BigDecimal.class) {
            return new BigDecimal(value);
        }
        if(targetType == BigInteger.class) {
            return BigInteger.valueOf(Long.parseLong(value));
        }
        if(targetType == Boolean.class || targetType == boolean.class) {
            return new Boolean(value);
        }
        if(targetType == boolean.class) {
            return new Boolean(value);
        }
        if(targetType == char.class) {
            return value.charAt(0);
        }
        if(DateHelper.isDateType(targetType)) {
            return DateHelper.parseDate(value,targetType,"yyyyMMdd","yyyy-MM-dd","yyyyMMddHHmmSS","yyyy-MM-dd HH:mm:ss","HH:mm:ss");
        }

        throw new IllegalArgumentException("cannot convert value:"+value +" to targetType:"+targetType);
    }
}
