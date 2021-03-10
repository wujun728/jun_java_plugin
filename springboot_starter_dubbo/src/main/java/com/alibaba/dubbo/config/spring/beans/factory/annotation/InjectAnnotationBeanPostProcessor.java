package com.alibaba.dubbo.config.spring.beans.factory.annotation;

import static org.springframework.core.annotation.AnnotatedElementUtils.getMergedAnnotation;

import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.reger.dubbo.annotation.Inject;

public class InjectAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
implements BeanFactoryAware, MergedBeanDefinitionPostProcessor, PriorityOrdered, ApplicationContextAware, BeanClassLoaderAware,
        DisposableBean {

    /**
     * The bean name of {@link InjectAnnotationBeanPostProcessor}
     */
    public static String BEAN_NAME = "Reger-Inject-Annotation-Bean-Post-Processor";
    
	private static final Logger logger = LoggerFactory.getLogger(InjectAnnotationBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;
    
	private final InjectBeanPostProcessor injectBeanPostProcessor=new InjectBeanPostProcessor();

    private final ConcurrentMap<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<String, InjectionMetadata>(256);

    private final ConcurrentMap<String, ReferenceBean<?>> referenceBeansCache = new ConcurrentHashMap<String, ReferenceBean<?>>();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		injectBeanPostProcessor.setBeanFactory(beanFactory);
	}
	
    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeanCreationException {

        InjectionMetadata metadata = findReferenceMetadata(beanName, bean.getClass(), pvs);
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @Reference dependencies failed", ex);
        }
        return pvs;
    }


    private List<InjectionMetadata.InjectedElement> findFieldReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        ReflectionUtils.doWithFields(beanClass, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

            	Inject inject = findReferenceAnnotation(field);
                if (inject == null) {
                	return;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    logger.warn("@Reference 静态方法不支持注释: {}" , field);
                    return;
                }
            	Class<?> requiredType = field.getDeclaringClass();
            	Object bean= getBean(inject, requiredType);
                if(bean!=null){
                	elements.add(injectBeanPostProcessor.createAutowiredFieldElement(field, true));
                	return;
                }
                elements.add(new ReferenceFieldElement(field, inject.value()));
             }

        });

        return elements;

    }

    private List<InjectionMetadata.InjectedElement> findMethodReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        ReflectionUtils.doWithMethods(beanClass, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(method);

                if (!BridgeMethodResolver.isVisibilityBridgeMethodPair(method, bridgedMethod)) {
                    return;
                }
                
            	Inject inject = findReferenceAnnotation(bridgedMethod);
                if (inject == null) {
                	return;
                }
                if(!method.equals(ClassUtils.getMostSpecificMethod(method, beanClass))){
                	return;
                }
                if (Modifier.isStatic(method.getModifiers())) {
                    logger.warn("@Reference 静态方法不支持注释: {}" , method);
                    return;
                }
                if (method.getParameterTypes().length == 0) {
                	logger.warn("@Reference  注释应该只使用有参数的方法: {}" , method);
                	return;
                }
                if(method.getParameterTypes().length>1){
                	logger.warn("@Reference  注释应该只使用有一个参数的方法: {}" , method);
                	throw new IllegalArgumentException("@Reference  注释应该只使用有一个参数的方法");
                }
            	Class<?> requiredType = method.getParameterTypes()[0];
            	Object bean= getBean(inject, requiredType);
            	PropertyDescriptor pd = BeanUtils.findPropertyForMethod(bridgedMethod, beanClass);
                if(bean!=null){
                	elements.add(injectBeanPostProcessor.createAutowiredMethodElement(bridgedMethod, true, pd));
                }else {
                	elements.add(new ReferenceMethodElement(method, pd, inject.value()));
                }
            }
        });

        return elements;

    }
    private Object getBean(Inject inject,Class<?> requiredType) {
    	String beanName = inject.name();
    	try {
    		if(StringUtils.isEmpty(beanName)){
    			return applicationContext.getBean(requiredType);
    		}else{
    			return applicationContext.getBean(beanName, requiredType);
    		}
		} catch (Exception e) {
			logger.debug("从spring上下文注入资源异常  beanName={} ,requiredType={} ", beanName, requiredType, e);
			return null;
		}
	}


    private InjectionMetadata buildReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        elements.addAll(findFieldReferenceMetadata(beanClass));

        elements.addAll(findMethodReferenceMetadata(beanClass));

        return new InjectionMetadata(beanClass, elements);

    }

    private InjectionMetadata findReferenceMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        // Fall back to class name as cache key, for backwards compatibility with custom callers.
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        // Quick check on the concurrent map first, with minimal locking.
        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildReferenceMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
                                "] for reference metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return metadata;
    }

    private Inject findReferenceAnnotation(AccessibleObject accessibleObject) {

        if (accessibleObject.getAnnotations().length > 0) {
        	Inject inject = getMergedAnnotation(accessibleObject, Inject.class);
            return inject;
        }

        return null;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (beanType != null) {
            InjectionMetadata metadata = findReferenceMetadata(beanName, beanType, null);
            metadata.checkConfigMembers(beanDefinition);
        }
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public void destroy() throws Exception {

        for (ReferenceBean<?> referenceBean : referenceBeansCache.values()) {
            if (logger.isInfoEnabled()) {
                logger.info(referenceBean + " was destroying!");
            }
            referenceBean.destroy();
        }

        injectionMetadataCache.clear();
        referenceBeansCache.clear();

        if (logger.isInfoEnabled()) {
            logger.info(getClass() + " was destroying!");
        }

    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private class ReferenceMethodElement extends InjectionMetadata.InjectedElement {

        private final Method method;

        private final Reference reference;

        protected ReferenceMethodElement(Method method, PropertyDescriptor pd, Reference reference) {
            super(method, pd);
            this.method = method;
            this.reference = reference;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            Class<?> referenceClass = pd.getPropertyType();

            Object referenceBean = buildReferenceBean(reference, referenceClass);

            ReflectionUtils.makeAccessible(method);

            method.invoke(bean, referenceBean);

        }

    }

    private class ReferenceFieldElement extends InjectionMetadata.InjectedElement {

        private final Field field;

        private final Reference reference;

        protected ReferenceFieldElement(Field field, Reference reference) {
            super(field, null);
            this.field = field;
            this.reference = reference;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            Class<?> referenceClass = field.getType();

            Object referenceBean = buildReferenceBean(reference, referenceClass);

            ReflectionUtils.makeAccessible(field);

            field.set(bean, referenceBean);

        }

    }

    private Object buildReferenceBean(Reference reference, Class<?> referenceClass) throws Exception {

        String referenceBeanCacheKey = generateReferenceBeanCacheKey(reference, referenceClass);

        ReferenceBean<?> referenceBean = referenceBeansCache.get(referenceBeanCacheKey);

        if (referenceBean == null) {

            ReferenceBeanBuilder beanBuilder = ReferenceBeanBuilder
                    .create(reference, classLoader, applicationContext)
                    .interfaceClass(referenceClass);

            referenceBean = beanBuilder.build();

            referenceBeansCache.putIfAbsent(referenceBeanCacheKey, referenceBean);

        }


        return referenceBean.get();
    }

    private static String generateReferenceBeanCacheKey(Reference reference, Class<?> beanClass) {

        String interfaceName = resolveInterfaceName(reference, beanClass);

        String key = reference.group() + "/" + interfaceName + ":" + reference.version();

        return key;

    }

    private static String resolveInterfaceName(Reference reference, Class<?> beanClass)
            throws IllegalStateException {

        String interfaceName;
        if (!"".equals(reference.interfaceName())) {
            interfaceName = reference.interfaceName();
        } else if (!void.class.equals(reference.interfaceClass())) {
            interfaceName = reference.interfaceClass().getName();
        } else if (beanClass.isInterface()) {
            interfaceName = beanClass.getName();
        } else {
            throw new IllegalStateException(
                    "The @Reference undefined interfaceClass or interfaceName, and the property type "
                            + beanClass.getName() + " is not a interface.");
        }

        return interfaceName;

    }

}
