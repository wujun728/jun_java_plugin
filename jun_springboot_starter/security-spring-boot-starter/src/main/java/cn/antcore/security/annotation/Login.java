package cn.antcore.security.annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 接口是否登录拦截
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Login {

    /**
     * true 需要登录， false 不需要登录
     * @return 默认 true
     */
    boolean value() default true;
}
