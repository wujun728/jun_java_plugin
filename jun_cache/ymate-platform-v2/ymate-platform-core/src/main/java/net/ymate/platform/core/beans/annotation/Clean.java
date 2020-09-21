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

import net.ymate.platform.core.beans.intercept.IInterceptor;

import java.lang.annotation.*;

/**
 * 拦截器清理
 *
 * @author 刘镇 (suninformation@163.com) on 15/7/1 上午11:13
 * @version 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Clean {

    /**
     * @return 设置需要清理的类拦截器，若不提供则默认清理全部类拦截器
     */
    Class<? extends IInterceptor>[] value() default {};

    /**
     * @return 拦截器清理方式
     */
    IInterceptor.CleanType type() default IInterceptor.CleanType.ALL;
}
