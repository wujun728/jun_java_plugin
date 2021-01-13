package com.kvn.poi;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author wzy on 2017/6/2.
 */
public class MockUtil {
    /************************************** 随机实例化 **************************************/
    private static final RandomStringUtils STRING_RANDOM = new RandomStringUtils();
    private static final Random RANDOM = new Random();

    /**
     * 初始化 基础类型 或者 基础类型的包装类型
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private static <T> T randomInstancePrimitiveClass(Class<T> clazz) throws Exception {
        int numberLength = 100000; // 数字保留5位

        String className = clazz.getName();
        switch (className) {
            case "java.lang.Boolean":
            case "boolean":
                return (T) Boolean.valueOf(RANDOM.nextBoolean());
            case "java.lang.Character":
            case "char":
                return (T) Character.valueOf((char) (RANDOM.nextInt() % 128));
            case "java.lang.Byte":
            case "byte":
                return (T) Byte.valueOf((byte) Math.abs((RANDOM.nextInt() % 128)));
            case "java.lang.Short":
            case "short":
                return (T) Short.valueOf((short) Math.abs((RANDOM.nextInt() % 32767)));
            case "java.lang.Integer":
            case "int":
                return (T) Integer.valueOf(Math.abs(RANDOM.nextInt() % numberLength));
            case "java.lang.Long":
            case "long":
                return (T) Long.valueOf(Math.abs(RANDOM.nextLong() % numberLength));
            case "java.lang.Float":
            case "float":
                return (T) Float.valueOf(Math.abs(RANDOM.nextFloat()));
            case "java.lang.Double":
            case "double":
                return (T) Double.valueOf(Math.abs(RANDOM.nextDouble()));
            default:
                throw new RuntimeException(className + "不支持，bug");
        }

    }

    /**
     * 实例化 Collection类及子类（List、Set）
     * @param clazz 被实例化的类
     * @param instance 被实例化的类（clazz）的实例
     * @return
     * @throws Exception
     */
    private static <T> T randomInstanceCollectionClass(Class<T> clazz, T instance) throws Exception {
        // Set
        if (clazz.equals(Set.class) || clazz.equals(HashSet.class)) {
            Set set = new HashSet();
            set.add(randomInstanceOfNonCollection(instance.getClass()));
            return (T) set;
        }
        // List
        if (clazz.equals(List.class) || clazz.equals(ArrayList.class)) {
            List list = new ArrayList();
            list.add(randomInstanceOfNonCollection(instance.getClass()));
            return (T) list;
        }
        // Collection
        if (clazz.equals(Collection.class)) {
            Collection collection = new ArrayList();
            collection.add(randomInstanceOfNonCollection(instance.getClass()));
            return (T) collection;
        }

        throw new RuntimeException("randomInstanceCollectionClass()异常，class:" + clazz + "不是集合类型");
    }

    /**
     * 随机实例化Collection及子类
     * @param collectionClass 被实例化的集合类
     * @param genericClass
     * @return
     * @throws Exception
     */
    private static <T> T randomInstanceCollectionClass(Class<T> collectionClass, Class<?> genericClass) throws Exception {
        // Set
        if (collectionClass.equals(Set.class) || collectionClass.equals(HashSet.class)) {
            Set set = new HashSet();
            set.add(randomInstanceOfNonCollection(genericClass));
            return (T) set;
        }
        // List
        if (collectionClass.equals(List.class) || collectionClass.equals(ArrayList.class)) {
            List list = new ArrayList();
            list.add(randomInstanceOfNonCollection(genericClass));
            return (T) list;
        }
        // Collection
        if (collectionClass.equals(Collection.class)) {
            Collection collection = new ArrayList();
            collection.add(randomInstanceOfNonCollection(genericClass));
            return (T) collection;
        }

        throw new RuntimeException("randomInstanceCollectionClass()异常，class:" + collectionClass + "不是集合类型");
    }

    /**
     * 实例化非Collection类型的类。<br/>
     * Collection类的实例化请调 {@link #randomInstanceCollectionClass(Class, Class)}
     * @param clazz 被实例化的类，Note:不能为Collection类型
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static <T> T randomInstanceOfNonCollection(Class<T> clazz) throws Exception {
        /** Collection及子类（List、Set） */
        if (Collection.class.isAssignableFrom(clazz)) {
            throw new RuntimeException("");
        }

        // 基础类型 或者 基础类型的包装类型
        if (isPrimitiveOrWrapClass(clazz)) {
            return randomInstancePrimitiveClass(clazz);
        }
        // 字符串
        if (clazz == String.class) {
            return (T) STRING_RANDOM.randomAlphabetic(4);
        }
        // 日期
        if (clazz == Date.class) {
            return (T) new Date();
        }
        if (clazz == Timestamp.class) {
            return (T) new Timestamp(System.currentTimeMillis());
        }
        if(clazz == BigDecimal.class){
        	return (T) new BigDecimal(RandomUtils.nextDouble(0, 100));
        }
        // 数组
        if (clazz.isArray()) {
            Object array = Array.newInstance(clazz.getComponentType(), 1);
            Array.set(array, 0, randomInstanceOfNonCollection(clazz.getComponentType()));
            return (T) array;
        }
        // 枚举
        if (clazz.isEnum()) {
            return clazz.getEnumConstants()[0];
        }

        T instance = clazz.newInstance();

        // 其他:一般的复杂对象
        try {
            for (Class<?> type = clazz; type != Object.class; type = type.getSuperclass()) {
                Method[] methods = type.getDeclaredMethods();
                for (Method method : methods) {
                    /**
                     * 通过setter方法来设置对象属性的值
                     * 注意：POJO中getter、setter必需成对出现，使用IDE自动生成即可。否则将出现不可预知的错误。
                     * */
                    if (!method.getName().startsWith("set") || method.getParameterTypes().length != 1) {
                        continue;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != 1 || parameterTypes[0] == clazz) {
                        continue;
                    }

                    String getMethodName = "g" + method.getName().substring(1);
                    Method getMethod = type.getDeclaredMethod(getMethodName);

                    method.invoke(instance, randomMethodReturnTypeInstance(getMethod));
                }
            }
            return instance;
        } catch (Exception e) {
            System.err.println("can not instantiate, type: " + clazz + ", e: " + e.getMessage());
            return null;
        }

    }


    /**
     * 随机生成 method方法的返回对象
     * @param method
     * @return
     * @throws Exception
     */
    public static Object randomMethodReturnTypeInstance(Method method) throws Exception{
        Class<?> returnClass = method.getReturnType();


        // 集合类型的返回值
        if(Collection.class.isAssignableFrom(returnClass)){
            ParameterizedType pt = null;
            try {
                pt = (ParameterizedType) method.getGenericReturnType();
            } catch (ClassCastException e) {
                throw new RuntimeException("ClassCastException，类[" + method.getDeclaringClass() +"]的方法[" + method.getName() + "]的返回类型是集合类，需要带泛型，否则不能实例化。==>" + e.getMessage());
            }
            Class<?> genericClassOfReturnClass = (Class) pt.getActualTypeArguments()[0];
            return randomInstanceCollectionClass(returnClass, genericClassOfReturnClass);
        }
        
        // Map类型的返回值
        if(Map.class.isAssignableFrom(returnClass)){
        	ParameterizedType pt = null;
            try {
                pt = (ParameterizedType) method.getGenericReturnType();
            } catch (ClassCastException e) {
                throw new RuntimeException("ClassCastException，类[" + method.getDeclaringClass() +"]的方法[" + method.getName() + "]的返回类型是Map类，需要带泛型，否则不能实例化。==>" + e.getMessage());
            }
        	Class genericClassOfKey = (Class) pt.getActualTypeArguments()[0];
        	Class genericClassOfValue = (Class) pt.getActualTypeArguments()[1];
        	return randomInstanceOfMap(returnClass, genericClassOfKey, genericClassOfValue);
        }



        // 其他
        return randomInstanceOfNonCollection(returnClass);
    }
    
    private static Object randomInstanceOfMap(Class<?> returnClass, Class genericClassOfKey, Class genericClassOfValue) throws Exception {
		Map map = new HashMap();
		// 只支持简单类型的key
		if(String.class.isAssignableFrom(genericClassOfKey) || isWrapClass(genericClassOfKey)){
			Object key = randomInstanceOfNonCollection(genericClassOfKey);
			Object value = randomInstanceOfNonCollection(genericClassOfValue);
			map.put(key, value);
			return map;
		}
		
		throw new RuntimeException("构造Map类型的随机返回值，要求key是String 或 基本类型的包装类型");
	}

	/**
	 * 决断clz是否是基本类型 或者 基本类型的包装类型
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitiveOrWrapClass(Class clz){
		if(clz.isPrimitive()){
			return true;
		}

		return isWrapClass(clz);
	}

	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}
	
	
}