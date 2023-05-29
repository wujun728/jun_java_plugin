package com.jun.plugin.filter;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: Access 
 * @Description: 
 * @author: renkai721
 * @date: 2018年6月25日 下午4:55:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Access {
    String[] value() default {};
}
