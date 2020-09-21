package org.coody.framework.core.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.coody.framework.core.annotation.Around;
import org.coody.framework.core.annotation.Arounds;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.constant.FrameworkConstant;
import org.coody.framework.core.entity.AspectEntity;
import org.coody.framework.core.loader.iface.IcopLoader;
import org.coody.framework.core.util.MethodSignUtil;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;

/**
 * 切面加载器
 * 
 * @author Coody
 *
 */
public class AspectLoader implements IcopLoader {

	private static final Logger logger = Logger.getLogger(AspectLoader.class);
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
			Annotation initBean = PropertUtil.getAnnotation(cla, AutoBuild.class);
			if (initBean == null) {
				continue;
			}
			Method[] methods = cla.getDeclaredMethods();
			if (StringUtil.isNullOrEmpty(methods)) {
				continue;
			}
			for (Method method : methods) {
				if(Modifier.isStatic(method.getModifiers())||Modifier.isAbstract(method.getModifiers())){
					continue;
				}
				if(StringUtil.isNullOrEmpty(method.getAnnotations())){
					continue;
				}
				List<Annotation> arounds=PropertUtil.getAnnotations(method, Around.class);
				if (StringUtil.isNullOrEmpty(arounds)) {
					List<Annotation> aroundParents=PropertUtil.getAnnotations(method, Arounds.class);
					if(StringUtil.isNullOrEmpty(aroundParents)){
						continue;
					}
					arounds=new ArrayList<Annotation>();
					for(Annotation aroundParent:aroundParents){
						Annotation[] aroundTemps=PropertUtil.getAnnotationValue(aroundParent,"value");
						if(StringUtil.isNullOrEmpty(aroundTemps)){
							continue;
						}
						arounds.addAll(Arrays.asList(aroundTemps));
					}
				}
				for (Annotation around : arounds) {
					Class<?>[] annotationClass=PropertUtil.getAnnotationValue(around, "annotationClass");
					String classMappath=PropertUtil.getAnnotationValue(around, "classMappath");
					String methodMappath=PropertUtil.getAnnotationValue(around, "methodMappath");
					if (StringUtil.isAllNull(annotationClass, classMappath, methodMappath)) {
						continue;
					}
					logger.debug("初始化切面方法 >>"+MethodSignUtil.getMethodKey(cla, method));
					AspectEntity aspectEntity = new AspectEntity();
					// 装载切面控制方法
					aspectEntity.setAnnotationClass(annotationClass);
					aspectEntity.setMethodMappath(classMappath);
					aspectEntity.setClassMappath(methodMappath);
					aspectEntity.setAspectInvokeMethod(method);
					String methodKey = MethodSignUtil.getMethodUnionKey(method);
					FrameworkConstant.writeToAspectMap(methodKey, aspectEntity);
				}
			}
		}
	}

}
