package com.zccoder.java.book1.ch0.basic;

import java.lang.annotation.*;

/**
 * 标题：被标注的类为线程安全的类<br>
 * 描述：被标注的类为线程安全的类<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadSafe {


}
