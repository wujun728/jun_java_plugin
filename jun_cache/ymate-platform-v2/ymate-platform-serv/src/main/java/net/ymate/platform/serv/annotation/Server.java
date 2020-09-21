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
package net.ymate.platform.serv.annotation;

import net.ymate.platform.serv.ICodec;
import net.ymate.platform.serv.IServer;
import net.ymate.platform.serv.nio.codec.NioStringCodec;
import net.ymate.platform.serv.nio.server.NioServer;

import java.lang.annotation.*;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/5 上午12:16
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Server {

    String name() default "default";

    Class<? extends ICodec> codec() default NioStringCodec.class;

    Class<? extends IServer> implClass() default NioServer.class;
}
