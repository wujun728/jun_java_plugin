package org.springrain.frame.common;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
/**
 * 重新设置SpringMVC的命名规则,直接使用类的全路径,方便混淆,不使用最后的简写
 * @author springrain
 *
 */
public class SpringMVCAnnotationBeanNameGenerator extends  AnnotationBeanNameGenerator  {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String className =definition.getBeanClassName();  
		return className;
	}

	
	
	
}
