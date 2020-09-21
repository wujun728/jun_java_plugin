/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.beans.annotation;

import java.lang.annotation.*;

/**
 * 声明一个类为代理类的注解
 *
 * @author 刘镇 (suninformation@163.com) on 15-3-8 下午1:59
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Proxy {

    /**
     * @return 指定代理类作用包路径范围，若未提供则默认全局范围
     */
    String packageScope() default "";

    /**
     * @return 指定代理类作用的自定义注解集合，若未提供则默认全部
     */
    Class<? extends Annotation>[] annotation() default {};

    /**
     * @return 执行顺序, 数值小的最先执行, 默认值为0表示按默认顺序
     */
    Order order() default @Order(0);
}
