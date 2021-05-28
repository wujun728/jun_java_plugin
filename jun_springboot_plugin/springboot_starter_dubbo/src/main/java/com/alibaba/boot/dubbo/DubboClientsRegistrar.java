package com.alibaba.boot.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by wuyu on 2017/4/18.
 */
public class DubboClientsRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private final static Logger logger = LoggerFactory.getLogger(DubboClientsRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        try {
            registerFeignClients(metadata, registry);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void registerFeignClients(AnnotationMetadata metadata, BeanDefinitionRegistry registry) throws ClassNotFoundException {
        String scan = resolve("${spring.dubbo.scan}");
        if (scan == null || StringUtils.isEmpty(scan) || scan.equalsIgnoreCase("${spring.dubbo.scan}")) {
            return;
        }
        for (String basePackage : scan.split(",")) {

            ClassPathScanningCandidateComponentProvider provider = getScanner();

            if (ClassUtils.isPresent("org.springframework.cloud.netflix.feign.FeignClient", this.getClass().getClassLoader())) {
                provider.addIncludeFilter(new AnnotationTypeFilter(FeignClient.class));
            }

            provider.addIncludeFilter(new AnnotationTypeFilter(DubboClient.class));
            Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                String beanClassName = candidateComponent.getBeanClassName();
                Class<?> aClass = ClassUtils.forName(beanClassName, DubboClientsRegistrar.class.getClassLoader());
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ReferenceBean.class);

                //全局超时时间
                String timeout = resolve("${spring.dubbo.timeout}");
                DubboClient dubboClient = aClass.getAnnotation(DubboClient.class);

                if (dubboClient != null) {
                    Reference reference = dubboClient.value();
                    beanDefinitionBuilder.addConstructorArgValue(reference);

                    //如果Reference注解不携带时间,将使用全局时间
                    if (reference.timeout() == 0) {
                        beanDefinitionBuilder.addPropertyValue("timeout", Integer.parseInt(timeout));
                    }

                    if (!StringUtils.isEmpty(dubboClient.protocol())) {
                        beanDefinitionBuilder.addPropertyValue("protocol", dubboClient.protocol());
                    }

                    if (!StringUtils.isEmpty(dubboClient.connections())) {
                        beanDefinitionBuilder.addPropertyValue("connections", Integer.parseInt(resolve(dubboClient.connections())));

                    }

                    if (!StringUtils.isEmpty(dubboClient.retries())) {
                        beanDefinitionBuilder.addPropertyValue("retries", Integer.parseInt(resolve(dubboClient.retries())));

                    }

                    if (!StringUtils.isEmpty(dubboClient.actives())) {
                        beanDefinitionBuilder.addPropertyValue("actives", Integer.parseInt(resolve(dubboClient.actives())));

                    }

                    if (!StringUtils.isEmpty(dubboClient.timeout())) {
                        //覆盖全局超时时间
                        beanDefinitionBuilder.addPropertyValue("timeout", Integer.parseInt(resolve(dubboClient.timeout())));
                    }

                    if (!StringUtils.isEmpty(reference.group())) {
                        beanDefinitionBuilder.addPropertyValue("group", resolve(reference.group()));
                    }

                    if (!StringUtils.isEmpty(reference.version())) {
                        beanDefinitionBuilder.addPropertyValue("version", resolve(reference.version()));
                    }

                    if (!StringUtils.isEmpty(reference.url())) {
                        beanDefinitionBuilder.addPropertyValue("url", resolve(reference.url()));
                    }

                    if (!StringUtils.isEmpty(reference.loadbalance())) {
                        beanDefinitionBuilder.addPropertyValue("loadbalance", resolve(reference.loadbalance()));
                    }
                }

                AbstractBeanDefinition definition = beanDefinitionBuilder
                        .addPropertyValue("interface", beanClassName)
                        .getBeanDefinition();
                beanDefinitionBuilder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                registry.registerBeanDefinition(getAliasName(beanClassName), definition);
            }
        }
    }

    protected String getAliasName(String className) {
        try {
            if (ClassUtils.isPresent("org.springframework.cloud.netflix.feign.FeignClient", this.getClass().getClassLoader())) {
                Class<?> client = ClassUtils.forName(className, DubboClientsRegistrar.class.getClassLoader());
                FeignClient feignClient = client.getAnnotation(FeignClient.class);
                if (feignClient != null && !StringUtils.isEmpty(feignClient.qualifier())) {
                    return feignClient.qualifier();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String name = className.substring(className.lastIndexOf(".") + 1);
        if (className.length() > 1) {
            return name.substring(0, 1).toLowerCase() + name.substring(1) + "DubboClient";
        }
        return className;
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false) {

            @Override
            protected boolean isCandidateComponent(
                    AnnotatedBeanDefinition beanDefinition) {
                if (beanDefinition.getMetadata().isIndependent()) {
                    // TODO until SPR-11711 will be resolved
                    if (beanDefinition.getMetadata().isInterface()
                            && beanDefinition.getMetadata()
                            .getInterfaceNames().length == 1
                            && Annotation.class.getName().equals(beanDefinition
                            .getMetadata().getInterfaceNames()[0])) {
                        try {
                            Class<?> target = ClassUtils.forName(
                                    beanDefinition.getMetadata().getClassName(),
                                    resourceLoader.getClassLoader());
                            return !target.isAnnotation();
                        } catch (Exception ex) {
                            this.logger.error(
                                    "Could not load target class: "
                                            + beanDefinition.getMetadata().getClassName(),
                                    ex);

                        }
                    }
                    return true;
                }
                return false;

            }
        };
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private String resolve(String value) {
        if (StringUtils.hasText(value)
                && this.resourceLoader instanceof ConfigurableApplicationContext) {
            return ((ConfigurableApplicationContext) this.resourceLoader).getEnvironment()
                    .resolvePlaceholders(value);
        }
        return value;
    }

    public String[] resolveArray(String[] array) {
        String[] resolveArray = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            resolveArray[i] = resolve(array[i]);
        }
        return resolveArray;
    }

}

