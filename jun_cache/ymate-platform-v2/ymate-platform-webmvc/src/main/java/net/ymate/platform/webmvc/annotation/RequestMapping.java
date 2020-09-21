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
package net.ymate.platform.webmvc.annotation;

import net.ymate.platform.webmvc.base.Type;

import java.lang.annotation.*;

/**
 * 声明控制器请求路径映射
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-10 下午9:12:59
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /**
     * @return 控制器请求路径映射
     */
    String value();

    /**
     * @return 允许的请求方式，默认为GET方式
     */
    Type.HttpMethod[] method() default {};

    /**
     * @return 请求头中必须存在的头名称
     */
    String[] header() default {};

    /**
     * @return 请求中必须存在的参数名称
     */
    String[] param() default {};
}
