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
 * 声明控制器方法默认返回视图对象, 仅在方法无返回值或返回值无效时使用
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/30 下午1:14
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseView {

    /**
     * @return 视图模板文件路径
     */
    String value() default "";

    /**
     * @return 视图文件类型
     */
    Type.View type() default Type.View.NULL;
}
