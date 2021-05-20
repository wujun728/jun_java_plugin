package com.ketayao.learn.javase.annotation;

import java.lang.annotation.*;

/**
 * 定义annotation
 * 
 */
// 加载在VM中，在运行时进行映射
@Retention(RetentionPolicy.RUNTIME)
// 限定此annotation只能标示方法
@Target(ElementType.METHOD)
public @interface AnnotationDefineForTestFunction {
}