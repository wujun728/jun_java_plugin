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

import net.ymate.platform.core.beans.proxy.IProxy;

import java.lang.annotation.*;

/**
 * 代理类清理, 用于禁止所有或指定类型的代理类
 *
 * @author 刘镇 (suninformation@163.com) on 16/3/21 下午11:53
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CleanProxy {

    /**
     * @return 设置需要清理的代理类，若不提供则默认清理全部代理
     */
    Class<? extends IProxy>[] value() default {};
}
