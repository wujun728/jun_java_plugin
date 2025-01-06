//package io.github.wujun728.db.annotation;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.Target;
//import java.util.Date;
//
//import static java.lang.annotation.ElementType.FIELD;
//import static java.lang.annotation.ElementType.METHOD;
//import static java.lang.annotation.RetentionPolicy.RUNTIME;
//
///**
// * 字段名及相关属性信息
// * @author 彭佳佳
// */
//@Target({METHOD, FIELD})
//@Retention(RUNTIME)
//public @interface Column {
//
//    /**
//     * 字段名称
//     * @return String
//     */
//    String name() default "";
//    boolean ignore() default false;
//    boolean parimryKey() default false;
//}