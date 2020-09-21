package org.coody.framework.core.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.exception.BeanInitException;
import org.coody.framework.core.exception.BeanNameCreateException;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.proxy.CglibProxy;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;

/**
 * Bean加载器
 * 
 * @author Coody
 *
 */
public class BeanLoader implements IcopLoader {
	
	private static final Logger logger = Logger.getLogger(BeanLoader.class);

	static CglibProxy proxy = new CglibProxy();

	@Override
	public void doLoader(Set<Class<?>> clazzs) throws Exception {
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return;
		}
		for (Class<?> cla : clazzs) {
			if (cla.isAnnotation()) {
				continue;
			}
			if (cla.isInterface()) {
				continue;
			}
			if(Modifier.isAbstract(cla.getModifiers())){
				continue;
			}
			if(cla.isEnum()){
				continue;
			}
			if(StringUtil.isNullOrEmpty(cla.getAnnotations())){
				continue;
			}
			Annotation autoBuild = PropertUtil.getAnnotation(cla, AutoBuild.class);
			if (StringUtil.isNullOrEmpty(autoBuild)) {
				continue;
			}
			List<String> beanNames = BeanContainer.getBeanNames(cla);
			if (StringUtil.isNullOrEmpty(beanNames)) {
				throw new BeanNameCreateException(cla);
			}
			Object bean = proxy.getProxy(cla);
			if (bean == null) {
				throw new BeanInitException(cla);
			}
			for (String beanName : beanNames) {
				if (StringUtil.isNullOrEmpty(beanName)) {
					continue;
				}
				logger.debug("初始化Bean >>"+beanName+":"+cla.getName());
				BeanContainer.setBean(beanName, bean);
			}
		}
	}

}
