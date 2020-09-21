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
package net.ymate.platform.persistence.mongodb;

import java.util.Map;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 上午10:00
 * @version 1.0
 */
public interface IMongoModuleCfg {

    /**
     * @return 返回默认数据源名称，默认值：default
     */
    String getDataSourceDefaultName();

    /**
     * @return 返回数据源自定义配置处理器
     */
    IMongoClientOptionsHandler getClientOptionsHandler();

    /**
     * @return 返回数据源配置映射
     */
    Map<String, MongoDataSourceCfgMeta> getDataSourceCfgs();

    /**
     * @return 返回默认数据源配置
     */
    MongoDataSourceCfgMeta getDefaultDataSourceCfg();

    /**
     * @param name 数据源名称
     * @return 返回指定名称的数据源配置
     */
    MongoDataSourceCfgMeta getDataSourceCfg(String name);
}
