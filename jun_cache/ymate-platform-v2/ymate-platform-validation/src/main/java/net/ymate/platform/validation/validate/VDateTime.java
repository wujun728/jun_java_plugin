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
package net.ymate.platform.validation.validate;

import net.ymate.platform.core.util.DateTimeUtils;

import java.lang.annotation.*;

/**
 * 日期参数验证注解
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/25 下午1:10
 * @version 1.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VDateTime {

    /**
     * @return 日期格式字符串
     */
    String pattern() default DateTimeUtils.YYYY_MM_DD_HH_MM_SS;

    /**
     * @return 自定义验证消息
     */
    String msg() default "";
}
