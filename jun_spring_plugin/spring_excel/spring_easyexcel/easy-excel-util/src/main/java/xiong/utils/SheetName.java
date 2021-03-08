package xiong.utils;

import java.lang.annotation.*;

/**
 * sheet名称
 * @author Wujun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
@Documented
@Inherited
public @interface SheetName {
    String value() default "";
}
