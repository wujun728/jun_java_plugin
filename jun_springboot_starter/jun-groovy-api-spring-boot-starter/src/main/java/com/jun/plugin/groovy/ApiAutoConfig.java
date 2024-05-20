package com.jun.plugin.groovy;

import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngineManager;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@ComponentScan(basePackages = "com.jun.plugin.groovy")
public class ApiAutoConfig  {

}