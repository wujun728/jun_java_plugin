package cn.springboot.config.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @Description 在方法上使用，用于指定使用哪个数据源
 * @author Wujun
 * @date Mar 17, 2017 9:04:49 AM  
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    
    /** 数据源名称 */
    DataSourceEnum value();
}
