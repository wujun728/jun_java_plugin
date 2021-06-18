package com.reger.dubbo.config;

import static org.springframework.beans.factory.support.BeanDefinitionReaderUtils.registerWithGeneratedName;
import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;
import static org.springframework.util.ClassUtils.resolveClassName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.config.AbstractConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.InjectAnnotationBeanPostProcessor;
import com.alibaba.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import com.alibaba.dubbo.config.spring.context.annotation.DubboClassPathBeanDefinitionScanner;
import com.alibaba.dubbo.config.spring.util.BeanRegistrar;
import com.reger.dubbo.annotation.Export;

public class AnnotationBean extends AbstractConfig implements DisposableBean, BeanFactoryPostProcessor,
		ResourceLoaderAware, EnvironmentAware, BeanClassLoaderAware {

	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(AnnotationBean.class);
	
	protected BeanDefinitionRegistry registry;
	
	private ResourceLoader resourceLoader;
	
	private Environment environment;
	
	private ClassLoader classLoader;
	
	private String[] annotationPackages;

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.registry = (BeanDefinitionRegistry) beanFactory;
		BeanRegistrar.registerInfrastructureBean(registry, InjectAnnotationBeanPostProcessor.BEAN_NAME, InjectAnnotationBeanPostProcessor.class);
		BeanRegistrar.registerInfrastructureBean(registry, ReferenceAnnotationBeanPostProcessor.BEAN_NAME, ReferenceAnnotationBeanPostProcessor.class);
	}

	public void setPackage(String annotationPackage) {
		if (StringUtils.hasText(annotationPackage)) {
			this.annotationPackages = trims(annotationPackage);
		} else {
			this.annotationPackages = new String[] {};
		}
	}

	protected void postProcessAnnotationPackageService() {
		if (this.annotationPackages.length == 0) {
			return;
		}
		DubboClassPathBeanDefinitionScanner definitionScanner = new DubboClassPathBeanDefinitionScanner( registry, environment, resourceLoader);
		definitionScanner.addIncludeFilter(new AnnotationTypeFilter(Service.class));
		Set<BeanDefinitionHolder> definitionHolders = definitionScanner.doScan(this.annotationPackages);
		for (BeanDefinitionHolder beanDefinitionHolder : definitionHolders) {
			Class<?> beanClass = resolveClass(beanDefinitionHolder);
			Service service = findAnnotation(beanClass, Service.class);
			this.registerServiceBean(service, beanClass, beanDefinitionHolder.getBeanName());
		}
		logger.debug("{} annotated @Service Components { {} } were scanned under package[{}]", definitionHolders.size(), definitionHolders, this.annotationPackages);
	}

	/**
	 * dubbo导出加有@Export的类
	 * @param bean bean
	 * @param beanName beanName
	 */
	protected void exportServiceBean(Object bean,String beanName) {
		Class<?> beanClass = this.getOriginalClass(bean);
		Export export=findAnnotation(beanClass, Export.class);
		if(export==null) {
			return;
		}
		this.exportServiceBean(export, beanClass, beanName);
	}

	protected void exportServiceBean(Export export,Class<?> beanClass,String beanName) {
		Class<?>[] interfacesClaz = beanClass.getInterfaces();
		for (Class<?> interfaces : interfacesClaz) {
			registerWithGeneratedName(ExportBean.build(export, beanName, interfaces), registry);
		}
	}
	
	private void registerServiceBean(Service service,Class<?> beanClass,String beanName) {
		Class<?> interfaceClass = resolveServiceInterfaceClass(beanClass, service);
		if(interfaceClass==null){
			Class<?>[] interfacess = beanClass.getInterfaces();
			Assert.isTrue(interfacess.length!=0, beanClass+"没有实现任何接口，不可以发布服务");
			for (Class<?> interfaces : interfacess) {
				registerWithGeneratedName(ExportBean.build(service, beanName, interfaces), registry);
			}
		}else{
			registerWithGeneratedName(ExportBean.build(service, beanName, interfaceClass), registry);
		}
	}


	private Class<?> resolveServiceInterfaceClass(Class<?> annotatedServiceBeanClass, Service service) {
		Class<?> interfaceClass = service.interfaceClass();
		if (void.class.equals(interfaceClass)) {
			interfaceClass = null;
			String interfaceClassName = service.interfaceName();
			if (StringUtils.hasText(interfaceClassName)) {
				if (ClassUtils.isPresent(interfaceClassName, classLoader)) {
					interfaceClass = resolveClassName(interfaceClassName, classLoader);
				}
			}
		}
		if(interfaceClass==null){
			return null;
		}
		Assert.isTrue(interfaceClass.isInterface(), "The type that was annotated @Service is not an interface!");
		return interfaceClass;
	}


    private Class<?> resolveClass(BeanDefinitionHolder beanDefinitionHolder) {
        BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
        return resolveClass(beanDefinition);

    }

    private Class<?> resolveClass(BeanDefinition beanDefinition) {
        String beanClassName = beanDefinition.getBeanClassName();
        return resolveClassName(beanClassName, classLoader);

    }

	/**
	 * 切包名字符串
	 * 
	 * @param annotationPackage  包名
	 * @return 切好后的字符串
	 */
	private String[] trims(String annotationPackage) {
		String[] tmpes = Constants.COMMA_SPLIT_PATTERN.split(annotationPackage);
		List<String> packages = new ArrayList<String>();
		for (String tmpe : tmpes) {
			tmpe = tmpe.trim();
			if (!tmpe.isEmpty()) {
				packages.add(tmpe);
			}
		}
		return packages.toArray(new String[] {});
	}


	/**
	 * 获取bean的原始类型
	 * 
	 * @param bean
	 *            输入的bean对象
	 * @return bean的原始类型
	 */
	private Class<?> getOriginalClass(Object bean) {
		if (AopUtils.isAopProxy(bean)) {
			return AopUtils.getTargetClass(bean);
		}
		return bean.getClass();
	}

	@Override
	public void destroy() throws Exception {
		logger.info("dubbo开始关闭....");
		ProtocolConfig.destroyAll();
		RegistryConfig.destroyAll();
	}

}
