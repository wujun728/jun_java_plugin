package cn.antcore.security.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * <p>接口权限 要成功执行本接口，必须用户登录</p>
 * <p>验证顺序 authority > roles > condition</p>
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ApiAuthorize {

    /**
     * 需要的权限
     *
     * @return 权限集合
     */
    @AliasFor(value = "authority")
    String[] value() default {};

    /**
     * 需要的权限
     *
     * @return 权限集合
     */
    @AliasFor(value = "value")
    String[] authority() default {};

    /**
     * 需要的角色
     *
     * @return 角色集合
     */
    String[] roles() default {};

    /**
     * 其他条件
     * @return 条件 支持SpEL表达式
     */
    String condition() default "";
}
