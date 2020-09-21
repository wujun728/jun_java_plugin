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

/**
 * @author 刘镇 (suninformation@163.com) on 15/12/2 上午2:28
 * @version 1.0
 */
public interface IRedisDataSourceAdapter {

    /**
     * 数据源适配器初始化
     *
     * @param cfgMeta Redis数据源配置参数
     * @throws Exception 可能产生的异常
     */
    void initialize(RedisDataSourceCfgMeta cfgMeta) throws Exception;

    /**
     * @return RedisCommands实例持有者接口对象
     */
    IRedisCommandsHolder getCommandsHolder();

    /**
     * @return 获取MongoDB数据源配置参数
     */
    RedisDataSourceCfgMeta getDataSourceCfgMeta();

    /**
     * 销毁数据源适配器
     */
    void destroy();
}
