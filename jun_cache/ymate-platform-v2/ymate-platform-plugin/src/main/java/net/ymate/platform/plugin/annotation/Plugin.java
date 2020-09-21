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
package net.ymate.platform.plugin.annotation;

import java.lang.annotation.*;

/**
 * 声明一个类作为插件启动类的注解
 *
 * @author 刘镇 (suninformation@163.com) on 15/3/19 下午6:41
 * @version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Plugin {

    /**
     * @return 插件唯一ID，若未填写则使用初始化类名称进行MD5加密后的值做为ID
     */
    String id() default "";

    /**
     * @return 插件名称
     */
    String name() default "";

    /**
     * @return 插件别名
     */
    String alias() default "";

    /**
     * @return 插件作者
     */
    String author() default "";

    /**
     * @return 联系邮箱
     */
    String email() default "";

    /**
     * @return 插件版本
     */
    String version() default "1.0.0";

    //

    /**
     * @return 是否加载后自动启动运行
     */
    boolean automatic() default true;

    /**
     * @return 插件描述
     */
    String description() default "";
}
