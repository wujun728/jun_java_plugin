package com.lianqu1990.springboot.web.version.mapping.annotation;

import com.lianqu1990.springboot.web.version.mapping.core.VersionOperator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author hanchao
 * @date 2018/3/9 12:50
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TerminalVersion {

    int[] terminals() default {};

    VersionOperator op() default VersionOperator.NIL;

    String version() default "";

}
