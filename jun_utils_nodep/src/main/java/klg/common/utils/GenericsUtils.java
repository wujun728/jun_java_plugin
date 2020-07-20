package klg.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



public class GenericsUtils {
	
	public static Class getInterfaceGenericType(Class i,int index){
		return null;
	}
	/**
	 * 获取接口的泛型,eg:<br>
	 * interface A extends Base<Apple> <br>
	 * Class appleClass=GenericsUtils.getInterfaceGenericType(A.class,0, 0);
	 * 实现类也可以
	 * @param clazz 
	 * @param iIndex 接口位置
	 * @param gIndex 泛型位置
	 * @return
	 */
	public static Class getInterfaceGenericType(Class clazz,int iIndex,int gIndex){
		ParameterizedType type=(ParameterizedType)clazz.getGenericInterfaces()[iIndex];
		System.out.println(clazz.getGenericInterfaces()[iIndex]);
		return (Class) type.getActualTypeArguments()[gIndex];
	}
	
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenericType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();// 得到泛型父类
		// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class, 如BuyerServiceBean extends
		// DaoSupport就返回Buyer和Contact类型
		Type[] parameters = ((ParameterizedType) genType)
				.getActualTypeArguments();
		if (index > parameters.length || index < 0) {
			throw new RuntimeException("你输入的索引号"
					+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		if (!(parameters[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) parameters[index];
	}

	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenericType(Class clazz) {
		{
		}
		return getSuperClassGenericType(clazz, 0);
	}

	@SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method, int index) {
		Type returnType = method.getGenericReturnType();
		if (returnType instanceof ParameterizedType) {
			ParameterizedType type = (ParameterizedType) returnType;
			Type[] typeArguments = type.getActualTypeArguments();
			if (index >= typeArguments.length || index < 0) {
				throw new RuntimeException("你输入的索引"
						+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) typeArguments[index];
		}
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method) {
		return getMethodGenericReturnType(method, 0);
	}

	@SuppressWarnings("unchecked")
	public static List getMethodGenericParameterTypes(Method method, int index) {
		List results = new ArrayList();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		if (index > genericParameterTypes.length || index < 0) {
			throw new RuntimeException("你输入的索引"
					+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
		}
		Type genericParamenterType = genericParameterTypes[index];
		if (genericParamenterType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericParamenterType;
			Type[] parameterArgTypes = aType.getActualTypeArguments();
			for (Type parameterArgType : parameterArgTypes) {
				Class parameterArgClass = (Class) parameterArgType;
				results.add(parameterArgClass);
			}
			return results;
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static List getMethodGenericParameterTypes(Method method) {
		return getMethodGenericParameterTypes(method, 0);
	}

	@SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field, int index) {
		Type genericFileType = field.getGenericType();
		if (genericFileType instanceof ParameterizedType) {
			ParameterizedType aType = (ParameterizedType) genericFileType;
			Type[] fieldArgTypes = aType.getActualTypeArguments();
			if (index > fieldArgTypes.length || index < 0) {
				throw new RuntimeException("你输入的索引"
						+ (index < 0 ? "不能小于0" : "超出了参数的总数"));
			}
			return (Class) fieldArgTypes[index];
		}
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field) {
		return getFieldGenericType(field, 0);
	}
}
