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

import com.mongodb.MongoClient;

/**
 * @author 刘镇 (suninformation@163.com) on 15/11/21 下午11:55
 * @version 1.0
 */
public interface IMongoDataSourceAdapter {

    /**
     * 数据源适配器初始化
     *
     * @param optionsHandler 数据源自定义配置处理器
     * @param cfgMeta        MongoDB数据源配置参数
     * @throws Exception 可能产生的异常
     */
    void initialize(IMongoClientOptionsHandler optionsHandler, MongoDataSourceCfgMeta cfgMeta) throws Exception;

    /**
     * @return 获取MongoDB数据源配置参数
     */
    MongoDataSourceCfgMeta getDataSourceCfgMeta();

    /**
     * @return 获取MongoDB客户端实例
     */
    MongoClient getMongoClient();

    /**
     * @return 获取默认数据库持有者对象
     */
    IMongoDatabaseHolder getDefaultDatabaseHolder();

    /**
     * @param databaseName 数据库名称
     * @return 获取指定数据库名称的持有者对象
     */
    IMongoDatabaseHolder getDatabaseHolder(String databaseName);

    /**
     * 销毁数据源适配器
     */
    void destroy();
}
