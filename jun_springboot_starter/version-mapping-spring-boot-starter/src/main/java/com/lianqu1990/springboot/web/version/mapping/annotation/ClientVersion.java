package com.lianqu1990.springboot.web.version.mapping.annotation;

import java.lang.annotation.*;

/**
 * @author hanchao
 * @date 2018/3/9 12:17
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClientVersion {
    TerminalVersion[] value() default {};

    String[]  expression() default {};//从string表达式解析，和上述效果一致
}
