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
package net.ymate.platform.persistence.jdbc.repo.annotation;

import net.ymate.platform.persistence.base.Type;

import java.lang.annotation.*;

/**
 * 声明一个类为存储器对象, 声明一个类方法开启存储器操作
 *
 * @author 刘镇 (suninformation@163.com) on 16/4/22 下午1:49
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Repository {

    /**
     * @return 数据源名称, 默认为空
     */
    String dsName() default "";

    /**
     * @return 从资源文件中加载item指定的配置项, 默认为空
     */
    String item() default "";

    /**
     * @return 自定义SQL配置
     */
    String value() default "";

    /**
     * @return 操作类型, 默认为查询
     */
    Type.OPT type() default Type.OPT.QUERY;
}
