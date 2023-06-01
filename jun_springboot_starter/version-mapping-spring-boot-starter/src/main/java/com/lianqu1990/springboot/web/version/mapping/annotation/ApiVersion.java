package com.lianqu1990.springboot.web.version.mapping.annotation;

import java.lang.annotation.*;

/**
 * 注解用于生成requestmappinginfo时候直接拼接路径
 * 规则，自动放置于路径开始部分；不做method做版本，避免难以维护
 * @author hanchao
 * @date 2018/3/9 11:00
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiVersion {
    String value() default "";
}
