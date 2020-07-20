package com.zccoder.java.book1.ch0.basic;

import java.lang.annotation.*;

/**
 * 标题：被标注的类为不可变类<br>
 * 描述：不可变类是线程安全的类<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ThreadSafe
public @interface Immutable {


}
