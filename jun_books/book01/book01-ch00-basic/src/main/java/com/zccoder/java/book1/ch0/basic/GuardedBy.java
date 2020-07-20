package com.zccoder.java.book1.ch0.basic;

import java.lang.annotation.*;

/**
 * 标题：顺序序列的同步策略<br>
 * 描述：线程只有在持有了一个特定的锁后，才能访问某个域或方法。使用@GuardedBy标识出每一个需要加锁的状态变量。<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardedBy {

    /**
     * 锁
     * <p>
     * 锁的可能值如下<br/>
     * 1.@GuardedBy("this")：是指包含在对象中的内部锁（方法或域是这个对象的一个成员）<br/>
     * 2.@GuardedBy("filedName")：是指与filedName引用的对象相关联的锁，
     * 它或者是一个隐式锁（filedName没有引用一个Lock），
     * 或者是一个显示锁（filedName引用了一个Lock）<br/>
     * 3.@GuardedBy("ClassName.filedName")：类似于@GuardedBy("filedName")，
     * 不过所引用的锁对象是存储在另一个类中的静态域<br/>
     * 4.@GuardedBy("methodName")：是指锁对象是methodName()方法的返回值<br/>
     * 5.@GuardedBy("ClassName.class")：是指ClassName类的直接量对象<br/>
     * </p>
     *
     * @return 锁
     */
    String value() default "";

}
