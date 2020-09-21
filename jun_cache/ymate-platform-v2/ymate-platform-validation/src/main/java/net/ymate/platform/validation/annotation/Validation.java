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
package net.ymate.platform.validation.annotation;

import java.lang.annotation.*;

/**
 * 验证模式配置注解
 *
 * @author 刘镇 (suninformation@163.com) on 2013-4-7 下午4:45:42
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validation {

    /**
     * 验证模式枚举：<br>
     * NORMAL - 短路式验证，即出现验证未通过就返回验证结果<br>
     * FULL   - 对目标对象属性进行全部验证后返回全部验证结果
     */
    enum MODE {
        NORMAL, FULL
    }

    /**
     * @return 验证模式，默认为NORMAL
     */
    MODE mode() default MODE.NORMAL;

    /**
     * @return 自定义I18N资源文件名称
     */
    String resourcesName() default "";
}
