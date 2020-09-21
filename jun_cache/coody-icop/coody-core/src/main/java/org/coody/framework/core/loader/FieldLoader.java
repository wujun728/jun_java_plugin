package org.coody.framework.core.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.exception.BeanNotFoundException;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;

/**
 * 字段加载器
 * 
 * @author Coody
 *
 */
public class FieldLoader implements IcopLoader {
	
	private static final Logger logger = Logger.getLogger(FieldLoader.class);

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		for (Object bean : BeanContainer.getBeans()) {
			List<Field> fields = loadFields(bean.getClass());
			if (StringUtil.isNullOrEmpty(fields)) {
				continue;
			}
			for (Field field : fields) {
				if (StringUtil.isNullOrEmpty(field.getAnnotations())) {
					continue;
				}
				Annotation autoBuild = PropertUtil.getAnnotation(field, AutoBuild.class);
				if (StringUtil.isNullOrEmpty(autoBuild)) {
					continue;
				}
				String beanName = PropertUtil.getAnnotationValue(autoBuild, "name");
				if (StringUtil.isNullOrEmpty(beanName)) {
					beanName = field.getType().getName();
				}
				if (!BeanContainer.contains(beanName)) {
					throw new BeanNotFoundException(beanName, bean.getClass());
				}
				field.setAccessible(true);
				Object writeValue = BeanContainer.getBean(beanName);
				logger.debug("注入字段 >>"+field.getName()+":"+bean.getClass().getName());
				field.set(bean, writeValue);
			}
		}
	}

	private static List<Field> loadFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>();
		Field[] fieldArgs = clazz.getDeclaredFields();
		for (Field f : fieldArgs) {
			fields.add(f);
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null) {
			return fields;
		}
		List<Field> childFields = loadFields(superClass);
		if (StringUtil.isNullOrEmpty(childFields)) {
			return fields;
		}
		fields.addAll(childFields);
		return fields;
	}

}
