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
package net.ymate.platform.cache.annotation;

import net.ymate.platform.cache.ICaches;
import net.ymate.platform.cache.IKeyGenerator;
import net.ymate.platform.cache.impl.DefaultKeyGenerator;

import java.lang.annotation.*;

/**
 * 将类中方法的执行结果进行缓存的注解
 *
 * @author 刘镇 (suninformation@163.com) on 15/10/29 下午7:22
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {

    /**
     * @return 缓存名称, 默认值为default
     */
    String cacheName() default "default";

    /**
     * @return 缓存Key, 若未设置则使用keyGenerator自动生成
     */
    String key() default "";

    /**
     * @return Key生成器接口实现类
     */
    Class<? extends IKeyGenerator> generator() default DefaultKeyGenerator.class;

    /**
     * @return 缓存作用域
     */
    ICaches.Scope scope() default ICaches.Scope.DEFAULT;

    /**
     * @return 缓存数据超时时间, 默认为0, 即缓存300秒
     */
    int timeout() default 0;
}
