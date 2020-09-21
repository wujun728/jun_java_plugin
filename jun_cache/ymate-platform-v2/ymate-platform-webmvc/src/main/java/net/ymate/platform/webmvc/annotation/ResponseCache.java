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

import net.ymate.platform.cache.ICaches;
import net.ymate.platform.webmvc.IWebCacheProcessor;
import net.ymate.platform.webmvc.impl.NullWebCacheProcessor;

import java.lang.annotation.*;

/**
 * 声明控制器方法返回视图对象的执行结果将被缓存
 *
 * @author 刘镇 (suninformation@163.com) on 16/1/31 下午10:51
 * @version 1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseCache {

    /**
     * @return 缓存名称, 默认值为default
     */
    String cacheName() default "default";

    /**
     * @return 缓存Key, 若未设置则自动生成
     */
    String key() default "";

    /**
     * @return 缓存作用域
     */
    ICaches.Scope scope() default ICaches.Scope.DEFAULT;

    /**
     * @return 自定义视图缓存处理器
     */
    Class<? extends IWebCacheProcessor> processorClass() default NullWebCacheProcessor.class;

    /**
     * @return 缓存数据超时时间, 默认为0, 即缓存300秒
     */
    int timeout() default 0;

    /**
     * @return 是否使用GZIP压缩
     */
    boolean useGZip() default true;
}
