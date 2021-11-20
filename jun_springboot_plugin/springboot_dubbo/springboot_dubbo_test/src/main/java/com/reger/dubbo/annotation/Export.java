package com.reger.dubbo.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

@Service
@Target(TYPE)
@Retention(RUNTIME)
public @interface Export {

	@AliasFor(annotation = Service.class)
	String value() default "";
	
    String version() default "";

    String group() default "";

    String path() default "";
    /**
     * 是否导出当前实体类到注册中心 true/false
     * @return 是否导出当前实体类到注册中心
     */
    String export() default "";

    String token() default "";
    /**
     * 是否标识为过期方法 true/false
     * @return 是否标识为过期方法
     */
    String deprecated() default "";

    /**
     * 是否动态注册  true/false
     * @return 是否动态注册
     */
    String dynamic() default "";

    String accesslog() default "";

    int executes() default 0;
    /**
     * 是否注册当前实体类到注册中心 true/false
     * @return 是否注册当前实体类到注册中心
     */
    String register() default "";

    int weight() default 0;

    String document() default "";

    int delay() default 0;

    String local() default "";

    String stub() default "";

    String cluster() default "";

    String proxy() default "";

    int connections() default 0;

    int callbacks() default 0;

    String onconnect() default "";

    String ondisconnect() default "";

    String owner() default "";

    String layer() default "";

    int retries() default 0;

    String loadbalance() default "";
    /**
     * 是否异步方法 true/false
     * @return 是否异步方法
     */
    String async() default "";

    int actives() default 0;

    /**
     * 是否已发出数据 true/false
     * @return 是否已发出数据
     */
    String sent() default "";

    String mock() default "";

    String validation() default "";

    int timeout() default 0;

    String cache() default "";

    String[] filter() default {};

    String[] listener() default {};

    String[] parameters() default {};

    String application() default "";

    String module() default "";

    String provider() default "";

    String[] protocol() default {};

    String monitor() default "";

    String[] registry() default {};
}
