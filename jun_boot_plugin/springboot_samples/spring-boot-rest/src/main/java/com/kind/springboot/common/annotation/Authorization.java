package com.kind.springboot.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function:在Controller的方法上使用此注解，该方法可检查用户是否登录，未登录返回401错误. <br/>
 * Date:     2016年8月11日 上午11:30:51 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

}
