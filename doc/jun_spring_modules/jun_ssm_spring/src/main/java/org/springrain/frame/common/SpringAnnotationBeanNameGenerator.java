package org.springrain.frame.common;

import org.springframework.beans.factory.config.BeanDefinition;
/**
 * 重新设置springbean的命名规则,暂时不用
 * @author springrain
 *
 */
@Deprecated
//public class SpringAnnotationBeanNameGenerator extends  AnnotationBeanNameGenerator  {
public class SpringAnnotationBeanNameGenerator  {
/*
	@Override
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		String s=super.generateBeanName(definition, registry);
		System.out.println(s);
		
		String className = definition.getBeanClassName();
		//System.out.println(className);
		className=className.substring(className.lastIndexOf(".")+1);
		if(className.toLowerCase().endsWith("impl")){
			className=className.substring(0, className.length()-4);
		}
		
		className=className.substring(0,1).toLowerCase() + className.substring(1);
		//System.out.println(className);

		
		return className;
	}
	*/
	//@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String className =definition.getBeanClassName();  
	
		className=className.substring(className.lastIndexOf(".")+1);
		if(className.toLowerCase().endsWith("impl")){
			className=className.substring(0, className.length()-4);
		}
		if((className.toLowerCase().endsWith("service")||className.toLowerCase().endsWith("dao"))==false){
			//return super.buildDefaultBeanName(definition);
		}
		
	
		className=className.substring(0,1).toLowerCase() + className.substring(1);
		return className;
	}

	
	
	
}
