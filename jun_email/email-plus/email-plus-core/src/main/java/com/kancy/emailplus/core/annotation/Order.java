package com.kancy.emailplus.core.annotation;

import java.lang.annotation.*;

/**
 * Order
 *
 * @author kancy
 * @date 2020/2/22 14:17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    /**
     * 值越大，优先级越高，越先加载
     * @return
     */
    int value() default 0;
}
