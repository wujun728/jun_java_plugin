package com.zh.springbootcondition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wujun
 * @date 2019/5/29
 */
@Configuration
public class ConditionConfig {

    /**
     * @Conditional
     * 一般用于配置类，可注解方法也可注解类，只有在指定满足条件下才会生效，
     * 如果是多个条件类则需要都满足即数组里的类的match皆返回true
     * @return
     */
    @Bean
    @Conditional(MyCondition.class)
    public CatBean catBean(){
        return new CatBean();
    }

    /**
     * @ConditionalOnBean
     * 容器中存在指定 Bean，则生效。
     * @return
     */
    @Bean
    @ConditionalOnBean(CatBean.class)
    public DogBean dogBean(){
        return new DogBean();
    }

    /**
     * @ConditionalOnBean
     * 容器中不存在指定 Bean，则生效。
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CatBean.class)
    public BirdBean birdBean(){
        return new BirdBean();
    }
}
