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
package net.ymate.platform.persistence.redis;

import net.ymate.platform.core.YMP;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/30 上午3:16
 * @version 1.0
 */
public interface IRedis {

    String MODULE_NAME = "persistence.redis";

    /**
     * @return 返回所属YMP框架管理器实例
     */
    YMP getOwner();

    /**
     * @return 返回Redis模块配置对象
     */
    IRedisModuleCfg getModuleCfg();

    IRedisDataSourceAdapter getDefaultDataSourceAdapter() throws Exception;

    IRedisDataSourceAdapter getDataSourceAdapter(String dataSourceName) throws Exception;

    <T> T openSession(IRedisSessionExecutor<T> executor) throws Exception;

    <T> T openSession(IRedisCommandsHolder commandsHolder, IRedisSessionExecutor<T> executor) throws Exception;
}
