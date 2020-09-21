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

import net.ymate.platform.webmvc.IParameterEscapeProcessor;
import net.ymate.platform.webmvc.base.Type;
import net.ymate.platform.webmvc.impl.DefaultParameterEscapeProcessor;

import java.lang.annotation.*;

/**
 * 将控制器方法参数转义的注解
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/29 下午7:31
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParameterEscape {

    /**
     * @return 字符串参数转义范围
     */
    Type.EscapeScope scope() default Type.EscapeScope.DEFAULT;

    /**
     * @return 通知父级注解当前方法或参数的转义操作将被忽略
     */
    boolean skiped() default false;

    /**
     * @return 字符串参数转义处理器
     */
    Class<? extends IParameterEscapeProcessor> processor() default DefaultParameterEscapeProcessor.class;
}
