package cn.antcore.security.web;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.annotation.Login;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * Delete请求
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
@Login
@Documented
@ApiAuthorize
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RequestMapping(method = {RequestMethod.DELETE})
public @interface DeleteRest {

    /**
     * 路由名字
     *
     * @return 路由集合
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    /**
     * 是否登录
     *
     * @return true需要登录，false不需要登录
     */
    @AliasFor(value = "value", annotation = Login.class)
    boolean login() default true;

    /**
     * 需要的权限
     *
     * @return 权限集合
     */
    @AliasFor(annotation = ApiAuthorize.class)
    String[] authority() default {};

    /**
     * 需要的角色
     *
     * @return 角色集合
     */
    @AliasFor(annotation = ApiAuthorize.class)
    String[] roles() default {};

    /**
     * 其他条件
     *
     * @return 条件 支持EL表达式
     */
    @AliasFor(annotation = ApiAuthorize.class)
    String condition() default "";

    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};
}
