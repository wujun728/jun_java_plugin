package klg.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;

public class ReflectionTools {
	static ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

	/**
	 * 获取参数名
	 * 
	 * @param method
	 * @return
	 */
	public static String[] getParamNames(Method method) {
		return parameterNameDiscoverer.getParameterNames(method);
	}

	public static MethodParameter getParam(Method method, String paramName) {
		String[] paramNames = getParamNames(method);
		for (int i = 0; i < paramNames.length; i++) {
			if (paramNames[i].equals(paramName)) {
				return new MethodParameter(method, i);
			}
		}
		return null;
	}

	public static List<MethodParameter> getParams(Method method, Class<?> annotation, boolean resolveParamName) {
		List<MethodParameter> methodParameters = new ArrayList<>();
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			for (int j = 0; j < annotations[i].length; j++) {
				if (annotation.isInstance(annotations[i][j])) {
					MethodParameter methodParameter = new MethodParameter(method, i);

					if (resolveParamName) {
						methodParameter.initParameterNameDiscovery(parameterNameDiscoverer);
					}
					methodParameters.add(methodParameter);
					break;
				}
			}
		}

		return methodParameters;
	}

	public static List<MethodParameter> getParamsWithName(Method method, Class<?> annotation) {
		return getParams(method, annotation, true);
	}

	/**
	 * 获取带有某个注解的参数坐标
	 * 
	 * @param method
	 * @param annotation
	 * @return
	 */
	public static List<Integer> getParamsIndex(Method method, Class<?> annotation) {
		List<Integer> indexs = new ArrayList<>();
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			for (int j = 0; j < annotations[i].length; j++) {
				if (annotation.isInstance(annotations[i][j])) {
					indexs.add(i);
					break;
				}
			}
		}
		return indexs;
	}

	/**
	 * 
	 * @param method
	 * @param argName
	 *            支持嵌套属性，比如 people.name
	 * @param args
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	
	public static Object getArgValue(Method method, String paramName, Object[] args) {
		if (paramName.contains(".")) {
			try {
				return getNestedArgValue(method, paramName, args);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
		for (int i = 0; i < args.length; i++) {
			if (paramNames[i].equals(paramName)) {
				return args[i];
			}
		}
		return null;
	}

	private static Object getNestedArgValue(Method method, String paramName, Object[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		String paramNameInMethod = paramName.split("\\.")[0];
		Object argValue = getArgValue(method, paramNameInMethod, args);
		String propertyName = paramName.substring(paramName.indexOf(".") + 1, paramName.length());

		return PropertyUtils.getNestedProperty(argValue, propertyName);

	}
}
