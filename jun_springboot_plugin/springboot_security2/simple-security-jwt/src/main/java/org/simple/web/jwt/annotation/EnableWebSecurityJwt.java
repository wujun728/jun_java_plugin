package org.simple.web.jwt.annotation;

import org.simple.web.jwt.config.WebSecurityConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 项目名称：web-security-jwt
 * 类名称：EnableWebSecurityJwt
 * 类描述：EnableWebSecurityJwt
 * 创建时间：2018/7/19
 *
 * @author guihuo   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({WebSecurityConfig.class})
public @interface EnableWebSecurityJwt {

}
