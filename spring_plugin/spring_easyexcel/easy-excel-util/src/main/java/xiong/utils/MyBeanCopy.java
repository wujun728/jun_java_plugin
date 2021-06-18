package xiong.utils;

import org.springframework.beans.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MyBeanCopy {

	private final class DateToStringConverter implements Converter<Date, String> {
        private DateFormat df ;
		private DateToStringConverter(String format) {
            df = new SimpleDateFormat(format);
		}
		@Override
		public String convert(Date source) {

			return df.format(source);
		}
	}

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static Object convertForProperty(Wrapper wrapper, Object object, Object value, String propertyName)
			throws TypeMismatchException {
		Object result;
		if (wrapper == null) {
			result = null;
		} else {
			wrapper.setWrappedInstance(object);
			result = wrapper.getBeanWrapper().convertForProperty(value, propertyName);
		}
		return result;
	}

	private static Object copyProperties(Object source, Object target) throws BeansException {
		Wrapper wrapper = new MyBeanCopy().new Wrapper(source);
		copyProperties(wrapper, source, target);
		return target;
	}

	/**
	 * Copy the property values of the given source bean into the target bean.
	 * <p>
	 * Note: The source and target classes do not have to match or even be derived from each other, as long as the properties match. Any bean
	 * properties that the source bean exposes but the target bean does not will silently be ignored.
	 * <p>
	 * This is just a convenience method. For more complex transfer needs, consider using a full BeanWrapper.
	 * 
	 * @param source
	 *            the source bean
	 * @param target
	 *            the target bean
	 * @throws BeansException
	 *             if the copying failed
	 */
	private static void copyProperties(Wrapper wrapper, Object source, Object target) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if ( sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 判断是否类型不一致
						if (value != null && !(targetPd.getPropertyType().isInstance(value))) {
							// 数据转型
							value = convertForProperty(wrapper, target, value, targetPd.getName());
						}
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					} catch (Exception ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}

	}

	private final class Wrapper {

		private GenericConversionService conversion;
		private BeanWrapperImpl bean;

		private Wrapper(Object object) {
			conversion = initDefaultConversionService();
			bean = initDefaultBeanWrapper(conversion, object);
		}

		private void setWrappedInstance(Object object) {
			bean.setWrappedInstance(object);
		}

		private GenericConversionService initDefaultConversionService() {
			GenericConversionService conversionService = new DefaultConversionService();
			conversionService.addConverter(new DateToStringConverter(DATE_FORMAT));
			return conversionService;
		}

		private BeanWrapperImpl initDefaultBeanWrapper(@SuppressWarnings("hiding") ConversionService conversion,
                                                       Object object) {
			BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
			beanWrapper.setConversionService(conversion);
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			dateFormat.setLenient(false);
			beanWrapper.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
			return beanWrapper;
		}

		private BeanWrapperImpl getBeanWrapper() {
			return bean;
		}
	}

	/**
	 * 复制源对象到目的对象
	 */
	private static void convert(Object source, Object target) {
		copyProperties(source, target);
	}

	public static <T> List<T> convert(List<?> sources, Class<T> targetClass) {
		List<?> sourcesObj = sources;
		if (sourcesObj == null) {
			sourcesObj = Collections.emptyList();
		}
		List<T> targets = new ArrayList<>(sourcesObj.size());
        MyBeanCopy.convert(sourcesObj, targets, targetClass);
		return targets;
	}

	private static <T> void convert(List<?> sources, List<T> targets, Class<T> targetClass) {
		if (targets == null) {
			return;
		}
		targets.clear();
		if (sources == null) {
			return;
		}
		for (Object obj : sources) {
			try {
				T target = targetClass.newInstance();
				targets.add(target);
				convert(obj, target);
			} catch (Exception e) {
                //do something
				return;
			}
		}
	}
}
