package io.github.wujun728.admin.common.annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderBy {
    String value() default "seq asc";
}
