package com.alibaba.boot.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;

import java.lang.annotation.*;

/**
 * Created by wuyu on 2016/10/31.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DubboClient {

    //Dubbo @Reference bug
    String protocol() default "";

    //String类型支持el表达式，可以通过配置文件动态配置
    String timeout() default "";

    String connections() default "";

    String retries() default "";

    String actives() default "";

    Reference value() default @Reference;
}
