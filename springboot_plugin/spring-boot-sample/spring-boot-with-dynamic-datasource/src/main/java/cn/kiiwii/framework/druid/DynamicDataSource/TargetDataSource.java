package cn.kiiwii.framework.druid.DynamicDataSource;

import java.lang.annotation.*;

/**
 * Created by zhong on 2016/11/14.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String name();
}