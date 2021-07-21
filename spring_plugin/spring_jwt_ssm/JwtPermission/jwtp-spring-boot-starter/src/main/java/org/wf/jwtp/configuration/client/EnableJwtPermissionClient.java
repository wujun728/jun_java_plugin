package org.wf.jwtp.configuration.client;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 开启自动配置
 * Created by wangfan on 2018-12-29 下午 2:44.
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import({JwtPermissionClientConfiguration.class})
public @interface EnableJwtPermissionClient {

}
