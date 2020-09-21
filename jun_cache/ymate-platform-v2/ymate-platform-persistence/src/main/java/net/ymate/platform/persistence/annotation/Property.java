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
package net.ymate.platform.persistence.annotation;

import net.ymate.platform.persistence.base.Type;

import java.lang.annotation.*;

/**
 * 声明一个类成员为数据实体属性
 *
 * @author 刘镇 (suninformation@163.com) on 2011-6-15 下午10:39:19
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Property {

    /**
     * @return 实现属性名称，默认采用当前成员名称
     */
    String name() default "";

    /**
     * @return 是否为自动增长
     */
    boolean autoincrement() default false;

    /**
     * @return 序列名称，适用于类似Oracle等数据库，配合autoincrement参数一同使用
     */
    String sequenceName() default "";

    /**
     * @return 允许为空
     */
    boolean nullable() default true;

    /**
     * @return 是否为无符号
     */
    boolean unsigned() default false;

    /**
     * @return 数据长度，默认0为不限制
     */
    int length() default 0;

    /**
     * @return 小数位数，默认0为无小数
     */
    int decimals() default 0;

    /**
     * @return 数据类型
     */
    Type.FIELD type() default Type.FIELD.UNKNOW;


}
