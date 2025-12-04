package com.mycompany.myproject.spring.selecter;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MyImportSelector.class,MyDeferredImportSelector.class,
        MyImportBeanDefinitionRegistrar.class, MyGeneralObject.class})
public class MySelecterConfiguration {
}
