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

import java.lang.annotation.*;

/**
 * 声明一个数据实体的索引
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/27 下午6:51
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Index {

    /**
     * @return 索引名称
     */
    String name();

    /**
     * @return 是否唯一索引
     */
    boolean unique() default true;

    /**
     * @return 索引字段名称集合
     */
    String[] fields();
}
