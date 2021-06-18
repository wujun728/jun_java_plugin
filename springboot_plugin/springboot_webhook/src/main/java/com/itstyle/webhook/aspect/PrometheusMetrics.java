package com.itstyle.webhook.aspect;
import java.lang.annotation.*;
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrometheusMetrics {
    /**
     *  默认为空,程序使用method signature作为Metric name
     *  如果name有设置值,使用name作为Metric name
     * @return
     */
    String name() default "";
}
