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
package net.ymate.platform.persistence.jdbc;

import net.ymate.platform.persistence.jdbc.dialect.IDialect;

import java.sql.Connection;

/**
 * 数据库Connection对象持有者接口，用于记录Connection原始的状态及与数据源对应关系
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-29 上午12:30:49
 * @version 1.0
 */
public interface IConnectionHolder {

    /**
     * @return 获取数据源配置参数
     */
    DataSourceCfgMeta getDataSourceCfgMeta();

    /**
     * @return 获取数据库连接对象
     */
    Connection getConnection();

    /**
     * 释放连接<br>
     * 注意：存在事务操作时严禁手动调用此方法，并由事务控制连接何时释放
     */
    void release();

    /**
     * @return 获取数据库方言
     */
    IDialect getDialect();
}
