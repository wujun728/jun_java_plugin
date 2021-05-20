package com.kind.springboot.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function:在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录用户. <br/>
 * Date:     2016年8月11日 上午11:31:20 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
