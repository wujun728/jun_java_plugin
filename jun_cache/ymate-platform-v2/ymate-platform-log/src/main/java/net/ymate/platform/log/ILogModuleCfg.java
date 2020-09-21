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
package net.ymate.platform.log;

import java.io.File;

/**
 * 日志记录器模块配置接口
 *
 * @author 刘镇 (suninformation@163.com) on 2012-11-27 下午7:01:57
 * @version 1.0
 */
public interface ILogModuleCfg {

    /**
     * @return 返回日志记录器配置文件
     */
    File getConfigFile();

    /**
     * @return 返回日志文件输出路径
     */
    File getOutputDir();

    /**
     * @return 返回默认日志记录器名称
     */
    String getLoggerName();

    /**
     * @return 返回ILogger接口实现类
     */
    Class<? extends ILogger> getLoggerClass();

    /**
     * @return 默认日志记录器是否允许控制台输出
     */
    boolean allowOutputConsole();
}
